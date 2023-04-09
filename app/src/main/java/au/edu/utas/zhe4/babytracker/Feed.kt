package au.edu.utas.zhe4.babytracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedBinding
import au.edu.utas.zhe4.babytracker.entities.Feed
import au.edu.utas.zhe4.babytracker.entities.FeedingSide
import au.edu.utas.zhe4.babytracker.entities.FeedingType
import au.edu.utas.zhe4.babytracker.entities.createFeed
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

const val FIREBASE_TAG = "FirebaseLogging"

class Feed : AppCompatActivity() {
    private lateinit var ui : ActivityFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(ui.root)

        //get feeding record object using id from intent
        val fRecordID = intent.getIntExtra(FEEDING_RECORD_INDEX, -1)
        var fRecordObj = Feed()
        if (fRecordID != -1) {
            fRecordObj = feedingRecords[fRecordID]
            // read in feeding record details and display on this screen
            when(fRecordObj.type) {
                FeedingType.BREASTFEEDING -> ui.rgFeedType.check(ui.rbBreastfeeding.id)
                else -> ui.rgFeedType.check(ui.rbBottleFeed.id)
            }
            ui.tvTimePickerCurrentTime.text = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(
                    fRecordObj.time!!
                ),
                TimeZone.getDefault().toZoneId(),
            ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).toString()
            when(fRecordObj.side) {
                FeedingSide.LEFT -> ui.rgFeedSide.check(ui.rbFeedSideLeft.id)
                else -> ui.rgFeedSide.check(ui.rbFeedSideRight.id)
            }
            ui.tvDuration.text = fRecordObj.duration.toString()
            ui.tilNote.editText!!.setText(fRecordObj.note)
        }

        val db = Firebase.firestore
        val feedingCollection = db.collection("feedings")

        ui.btSave.setOnClickListener{
            val id = if(fRecordID == -1) null else fRecordObj.id
            //get the user input
            val feedType : FeedingType = if(ui.rbBreastfeeding.isChecked)
                                            FeedingType.BREASTFEEDING
                                         else
                                            FeedingType.BOTTLE

            val strDateTime : String = ui.tvTimePickerCurrentTime.text.toString()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            var feedTime : LocalDateTime = LocalDateTime.parse(strDateTime, formatter)

            val feedSide : FeedingSide = if(ui.rbFeedSideLeft.isChecked)
                                            FeedingSide.LEFT
                                         else
                                            FeedingSide.RIGHT
            val feedDuration : Long = ui.tvDuration.text.toString().toLong()
            val note : String = ui.tietNote.text.toString()

            val feedObj : Feed = createFeed(id, feedType, feedTime, feedSide, feedDuration, note)

            // if already existed, update the record. otherwise add it
            when(true) {
                feedObj.id.isNullOrEmpty() -> {
                    // add to the database
                    feedingCollection
                        .add(feedObj)
                        .addOnSuccessListener {
                            Log.d(FIREBASE_TAG, "Document created with id ${it.id}")
                            feedObj.id = it.id
                        }
                        .addOnFailureListener {
                            Log.e(FIREBASE_TAG, "Error writing document", it)
                        }
                    finish()
                }
                else -> {
                    //update the database
                    feedingCollection.document(feedObj.id!!)
                        .set(feedObj)
                        .addOnSuccessListener {
                            Log.d(FIREBASE_TAG,
                                "Successfully updated feeding record ${feedObj.id}")
                        }
                        .addOnFailureListener {
                            Log.e(FIREBASE_TAG, "Error updated feeding record", it)
                        }
                    finish()
                }
            }
        }
    }
}