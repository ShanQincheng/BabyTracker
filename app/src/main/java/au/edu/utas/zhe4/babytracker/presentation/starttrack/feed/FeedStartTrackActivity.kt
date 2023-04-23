package au.edu.utas.zhe4.babytracker.presentation.starttrack.feed

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedStartTrackBinding
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModelFactory
import au.edu.utas.zhe4.babytracker.presentation.record.feed.FeedRecordActivity

class FeedStartTrackActivity : AppCompatActivity() {
    private lateinit var ui : ActivityFeedStartTrackBinding
    private lateinit var viewModel : FeedStartTrackViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = ActivityFeedStartTrackBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val adapter = FeedRecordsAdapter(context = this)
        val layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(
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

        ui.btStartTrack.setOnClickListener{
            val i = Intent(ui.root.context, FeedRecordActivity::class.java)

            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.readAllFeedRecords()
    }
}