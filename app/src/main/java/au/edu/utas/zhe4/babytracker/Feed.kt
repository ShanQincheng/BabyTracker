package au.edu.utas.zhe4.babytracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedBinding
import au.edu.utas.zhe4.babytracker.entities.Feed
import au.edu.utas.zhe4.babytracker.entities.FeedingSide
import au.edu.utas.zhe4.babytracker.entities.FeedingType
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

        val db = Firebase.firestore
        val feedingCollection = db.collection("feedings")

        ui.btSave.setOnClickListener{
            //get the user input
            val feedType : FeedingType = if(ui.rbBreastfeeding.isChecked)
                                            FeedingType.BREASTFEEDING
                                         else
                                            FeedingType.BOTTLE

            val strDateTime : String = ui.tvTimePickerCurrentTime.text.toString()
            val formatter = DateTimeFormatter.ofPattern("hh:mm")
            var feedTime : LocalDateTime = LocalDateTime.parse(strDateTime, formatter)

            val feedSide : FeedingSide = if(ui.rbFeedSideLeft.isChecked)
                                            FeedingSide.LEFT
                                         else
                                            FeedingSide.RIGHT
            val feedDuration : Duration = ui.tvDuration.text.toString()
                                            .toInt().toDuration(DurationUnit.MINUTES)
            val note : String = ui.tietNote.text.toString()

            val feedObj : Feed = Feed(feedType, feedTime, feedSide, feedDuration, note)

            //update the database
            feedingCollection.document(feedObj.id!!)
                .set(feedObj)
                .addOnSuccessListener {
                    Log.d(FIREBASE_TAG, "Successfully updated feeding ${feedObj.id}")
                    //return to the list
                    finish()
                }
        }
    }
}