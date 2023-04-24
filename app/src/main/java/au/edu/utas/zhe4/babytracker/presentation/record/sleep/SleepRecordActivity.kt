package au.edu.utas.zhe4.babytracker.presentation.record.sleep

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import au.edu.utas.zhe4.babytracker.databinding.ActivitySleepRecordBinding
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModelFactory
import au.edu.utas.zhe4.babytracker.presentation.record.DatePickerFragment
import au.edu.utas.zhe4.babytracker.presentation.record.TimePickerFragment

class SleepRecordActivity : AppCompatActivity() {
    private lateinit var ui : ActivitySleepRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivitySleepRecordBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val viewModel = ViewModelProvider(
            this,
            BabyTrackerViewModelFactory
        )[SleepRecordViewModel::class.java]

        updateViewModelByIntent(viewModel)

        showPage(viewModel)

        viewModel.sleepTime.observe(this, Observer {
            showSleepTime(viewModel)
        })

        viewModel.sleepStartTime.observe(this, Observer {
            showSleepStartTime(viewModel)
        })

        viewModel.sleepEndTime.observe(this, Observer {
            showSleepEndTime(viewModel)
        })

        ui.btTimePickerPopUp.setOnClickListener {
            val timePicker = TimePickerFragment(viewModel::setTime)
            timePicker.show(supportFragmentManager, "timePicker")

            val datePicker = DatePickerFragment(viewModel::setDate)
            datePicker.show(supportFragmentManager, "datePicker")
        }

        ui.btStartTimePicker.setOnClickListener {
            val timePicker = TimePickerFragment(viewModel::setStartTime)
            timePicker.show(supportFragmentManager, "timePicker")
        }

        ui.btEndTimePicker.setOnClickListener {
            val timePicker = TimePickerFragment(viewModel::setEndTime)
            timePicker.show(supportFragmentManager, "timePicker")
        }

        ui.tietNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.updateSleepNote(s.toString())
            }

        })

        ui.btSave.setOnClickListener{
            viewModel.saveOrUpdateToDatabase()
            finish()
        }
    }

    private fun updateViewModelByIntent(viewModel: SleepRecordViewModel) {
        if (intent.hasExtra("id")) {
            val id = intent.getStringExtra("id")
            viewModel.updateID(id!!)
        }

        if (intent.hasExtra("sleepTime")) {
            val slpTime = intent.getStringExtra("sleepTime").toString()
            viewModel.updateSleepTime(slpTime)
        }
        if (intent.hasExtra("sleepStartTime")) {
            val slpStartTime = intent.getStringExtra("sleepStartTime").toString()
            viewModel.updateSleepStartTime(slpStartTime)
        }
        if (intent.hasExtra("sleepEndTime")) {
            val slpEndTime = intent.getStringExtra("sleepEndTime").toString()
            viewModel.updateSleepEndTime(slpEndTime)
        }
        if (intent.hasExtra("sleepNote")) {
            val slpNote = intent.getStringExtra("sleepNote").toString()
            viewModel.updateSleepNote(slpNote)
        }
    }

    private fun showPage(viewModel : SleepRecordViewModel) {
        showSleepTime(viewModel)
        showSleepStartTime(viewModel)
        showSleepEndTime(viewModel)
        showSleepNote(viewModel)
    }

    private fun showSleepTime(viewModel : SleepRecordViewModel) {
        ui.tvTimePickerCurrentTime.text = viewModel.sleepTime.value!!
    }

    private fun showSleepStartTime(viewModel : SleepRecordViewModel) {
        ui.btStartTimePicker.text = viewModel.sleepStartTime.value!!
    }

    private fun showSleepEndTime(viewModel : SleepRecordViewModel) {
        ui.btEndTimePicker.text = viewModel.sleepEndTime.value!!
    }

    private fun showSleepNote(viewModel : SleepRecordViewModel) {
        ui.tietNote.setText(viewModel.sleepNote.value!!)
        ui.tietNote.setSelection(viewModel.sleepNote.value!!.length)
    }

}