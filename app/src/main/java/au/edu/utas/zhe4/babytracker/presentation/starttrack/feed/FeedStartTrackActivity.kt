package au.edu.utas.zhe4.babytracker.presentation.starttrack.feed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.zhe4.babytracker.FIREBASE_TAG
import au.edu.utas.zhe4.babytracker.FeedStartTrack
import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedBinding
import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedStartTrackBinding
import au.edu.utas.zhe4.babytracker.databinding.FeedRecordBinding
import au.edu.utas.zhe4.babytracker.entities.Feed
import au.edu.utas.zhe4.babytracker.feedingRecords
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModelFactory
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FeedStartTrackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ui = ActivityFeedStartTrackBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val adapter = FeedRecordsAdapter(context = this)
        val layoutManager = LinearLayoutManager(this)
        val viewModel = ViewModelProvider(
            this,
            BabyTrackerViewModelFactory
        )[FeedStartTrackViewModel::class.java]

        // tell RecyclerView how to display the items in vertical way
        ui.rvThisWeek.layoutManager = layoutManager
        // link the RecyclerView to the adapter
        ui.rvThisWeek.adapter = adapter

        viewModel.feedingRecords.observe(this, Observer {
            adapter.update(it)
        })

//        viewModel.
//        (ui.rvThisWeek.adapter as FeedRecordsAdapter).
//            notifyItemRangeChanged(0, viewModel.feedingRecords.size)

        ui.btStartTrack.setOnClickListener{
//            val i = Intent(ui.root.context, au.edu.utas.zhe4.babytracker.Feed::class.java)
//            startActivity(i)
            val ui = ActivityFeedBinding.inflate(layoutInflater)
            setContentView(ui.root)
        }
    }
}