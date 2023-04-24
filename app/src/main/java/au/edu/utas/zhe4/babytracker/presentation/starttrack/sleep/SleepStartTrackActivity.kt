package au.edu.utas.zhe4.babytracker.presentation.starttrack.sleep

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.zhe4.babytracker.databinding.ActivitySleepStartTrackBinding
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModelFactory
import au.edu.utas.zhe4.babytracker.presentation.record.nappy.NappyRecordActivity
import au.edu.utas.zhe4.babytracker.presentation.record.sleep.SleepRecordActivity
import au.edu.utas.zhe4.babytracker.presentation.starttrack.nappy.NappyStartTrackAdapter
import au.edu.utas.zhe4.babytracker.presentation.starttrack.nappy.NappyStartTrackViewModel

class SleepStartTrackActivity : AppCompatActivity() {
    private lateinit var ui : ActivitySleepStartTrackBinding
    private lateinit var viewModel : SleepStartTrackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = ActivitySleepStartTrackBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val adapter = SleepStartTrackAdapter(context = this)
        val layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(
            this,
            BabyTrackerViewModelFactory
        )[SleepStartTrackViewModel::class.java]

        // tell RecyclerView how to display the items in vertical way
        ui.rvThisWeek.layoutManager = layoutManager
        // link the RecyclerView to the adapter
        ui.rvThisWeek.adapter = adapter

        viewModel.sleepRecords.observe(this, Observer {
            adapter.update(it)
        })

        ui.btStartTrack.setOnClickListener{
            val i = Intent(ui.root.context, SleepRecordActivity::class.java)

            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.readAllSleepRecords()
    }
}