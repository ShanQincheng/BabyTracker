//package au.edu.utas.zhe4.babytracker.presentation.records.feed
//
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import au.edu.utas.zhe4.babytracker.FEEDING_RECORD_INDEX
//import au.edu.utas.zhe4.babytracker.FIREBASE_TAG
//import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedBinding
//import au.edu.utas.zhe4.babytracker.domain.Feed
//import au.edu.utas.zhe4.babytracker.domain.FeedingSide
//import au.edu.utas.zhe4.babytracker.domain.FeedingType
//import au.edu.utas.zhe4.babytracker.domain.createFeed
//import au.edu.utas.zhe4.babytracker.feedingRecords
//import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModelFactory
//import au.edu.utas.zhe4.babytracker.presentation.starttrack.feed.FeedStartTrackViewModel
//import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString
//import au.edu.utas.zhe4.babytracker.utils.TimeStringToLocalDateTime
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//import java.time.LocalDateTime
//
//class FeedRecordsActivity : AppCompatActivity() {
//    private lateinit var ui : ActivityFeedBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        ui = ActivityFeedBinding.inflate(layoutInflater)
//        setContentView(ui.root)
//
//        val viewModel = ViewModelProvider(
//            this,
//            BabyTrackerViewModelFactory
//        )[FeedStartTrackViewModel::class.java]
//
//        //get feeding record object using id from intent
//        val fRecordID = intent.getIntExtra(FEEDING_RECORD_INDEX, -1)
//        var fRecordObj = Feed()
//        if (fRecordID != -1) {
//            fRecordObj = viewModel.feedingRecords.value!![fRecordID]
//            // read in feeding record details and display on this screen
//            when(fRecordObj.type) {
//                FeedingType.BREASTFEEDING -> ui.rgFeedType.check(ui.rbBreastfeeding.id)
//                else -> ui.rgFeedType.check(ui.rbBottleFeed.id)
//            }
//            ui.tvTimePickerCurrentTime.text = LongToLocalDateTimeString(fRecordObj.time!!)
//            when(fRecordObj.side) {
//                FeedingSide.LEFT -> ui.rgFeedSide.check(ui.rbFeedSideLeft.id)
//                else -> ui.rgFeedSide.check(ui.rbFeedSideRight.id)
//            }
//            ui.tvDuration.text = fRecordObj.duration.toString()
//            ui.tilNote.editText!!.setText(fRecordObj.note)
//        }
//
//        val db = Firebase.firestore
//        val feedingCollection = db.collection("feedings")
//
//        ui.btSave.setOnClickListener{
//            val id = if(fRecordID == -1) null else fRecordObj.id
//            //get the user input
//            val feedType : FeedingType = if(ui.rbBreastfeeding.isChecked)
//                FeedingType.BREASTFEEDING
//            else
//                FeedingType.BOTTLE
//
//            val strDateTime : String = ui.tvTimePickerCurrentTime.text.toString()
//            val feedTime : LocalDateTime = TimeStringToLocalDateTime(strDateTime)
//
//            val feedSide : FeedingSide = if(ui.rbFeedSideLeft.isChecked)
//                FeedingSide.LEFT
//            else
//                FeedingSide.RIGHT
//            val feedDuration : Long = ui.tvDuration.text.toString().toLong()
//            val note : String = ui.tietNote.text.toString()
//
//            val feedObj : Feed = createFeed(id, feedType, feedTime, feedSide, feedDuration, note)
//
//            // if already existed, update the record. otherwise add it
//            when(true) {
//                feedObj.id.isNullOrEmpty() -> {
//                    // add to the database
//                    feedingCollection
//                        .add(feedObj)
//                        .addOnSuccessListener {
//                            Log.d(FIREBASE_TAG, "Document created with id ${it.id}")
//                            feedObj.id = it.id
//                        }
//                        .addOnFailureListener {
//                            Log.e(FIREBASE_TAG, "Error writing document", it)
//                        }
//
//                    viewModel.feedingRecords.value!!.add(feedObj)
//                    viewModel.feedingRecords.value!!.sortByDescending { it.time }
//                    finish()
//                }
//                else -> {
//                    //update the database
//                    feedingCollection.document(feedObj.id!!)
//                        .set(feedObj)
//                        .addOnSuccessListener {
//                            Log.d(
//                                FIREBASE_TAG,
//                                "Successfully updated feeding record ${feedObj.id}")
//                        }
//                        .addOnFailureListener {
//                            Log.e(FIREBASE_TAG, "Error updated feeding record", it)
//                        }
//
//                    viewModel.feedingRecords.value!![fRecordID] = feedObj
//                    viewModel.feedingRecords.value!!.sortByDescending { it.time }
//                    finish()
//                }
//            }
//        }
//    }
//}