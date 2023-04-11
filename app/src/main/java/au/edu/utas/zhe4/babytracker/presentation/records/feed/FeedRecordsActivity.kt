package au.edu.utas.zhe4.babytracker.presentation.records.feed

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import au.edu.utas.zhe4.babytracker.FEEDING_RECORD_INDEX
import au.edu.utas.zhe4.babytracker.FIREBASE_TAG
import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedBinding
import au.edu.utas.zhe4.babytracker.domain.Feed
import au.edu.utas.zhe4.babytracker.domain.FeedingSide
import au.edu.utas.zhe4.babytracker.domain.FeedingType
import au.edu.utas.zhe4.babytracker.domain.createFeed
import au.edu.utas.zhe4.babytracker.feedingRecords
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModelFactory
import au.edu.utas.zhe4.babytracker.presentation.starttrack.feed.FeedStartTrackViewModel
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.TimeStringToLocalDateTime
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class FeedRecordsActivity : AppCompatActivity() {
    private lateinit var ui : ActivityFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val viewModel = ViewModelProvider(
            this,
            BabyTrackerViewModelFactory
        )[FeedRecordsViewModel::class.java]

        viewModel.type.observe(this, Observer {
            when(viewModel.type.value) {
                FeedingType.BREASTFEEDING ->
                    ui.rgFeedType.check(ui.rbBreastfeeding.id)
                else ->
                    ui.rgFeedType.check(ui.rbBottleFeed.id)
            }
        })
    }
}