package au.edu.utas.zhe4.babytracker.presentation.starttrack.nappy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.zhe4.babytracker.databinding.ActivityNappyStartTrackBinding
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModelFactory
import au.edu.utas.zhe4.babytracker.presentation.record.nappy.NappyRecordActivity

class NappyStartTrackActivity : AppCompatActivity() {
    private lateinit var ui : ActivityNappyStartTrackBinding
    private lateinit var viewModel : NappyStartTrackViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = ActivityNappyStartTrackBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val adapter = NappyStartTrackAdapter(context = this)
        val layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(
            this,
            BabyTrackerViewModelFactory
        )[NappyStartTrackViewModel::class.java]

        // tell RecyclerView how to display the items in vertical way
        ui.rvThisWeek.layoutManager = layoutManager
        // link the RecyclerView to the adapter
        ui.rvThisWeek.adapter = adapter

        viewModel.nappyRecords.observe(this, Observer {
            adapter.update(it)
        })

        ui.btStartTrack.setOnClickListener{
            val i = Intent(ui.root.context, NappyRecordActivity::class.java)

            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.readAllNappyRecords()
    }
}