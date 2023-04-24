package au.edu.utas.zhe4.babytracker.presentation.record.nappy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import au.edu.utas.zhe4.babytracker.databinding.ActivityNappyRecordBinding
import au.edu.utas.zhe4.babytracker.domain.NappyCons
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModelFactory
import au.edu.utas.zhe4.babytracker.presentation.record.DatePickerFragment
import au.edu.utas.zhe4.babytracker.presentation.record.TimePickerFragment

class NappyRecordActivity() : AppCompatActivity() {
    private lateinit var ui : ActivityNappyRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityNappyRecordBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val viewModel = ViewModelProvider(
            this,
            BabyTrackerViewModelFactory
        )[NappyRecordViewModel::class.java]

        updateViewModelByIntent(viewModel)

        showPage(viewModel)

        viewModel.nappyTime.observe(this, Observer {
            showNappyTime(viewModel)
        })

        ui.rgNappyCons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                ui.rbNappyConsWet.id ->
                    viewModel.updateNappyCons(NappyCons.WET.toString())
                else ->
                    viewModel.updateNappyCons(NappyCons.WET_DIRTY.toString())
            }
        }

        ui.btTimePickerPopUp.setOnClickListener {
            val timePicker = TimePickerFragment(viewModel)
            timePicker.show(supportFragmentManager, "timePicker")

            val datePicker = DatePickerFragment(viewModel)
            datePicker.show(supportFragmentManager, "datePicker")
        }

        ui.rgNappyCons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                ui.rbNappyConsWet.id ->
                    viewModel.updateNappyCons(NappyCons.WET.toString())
                else ->
                    viewModel.updateNappyCons(NappyCons.WET_DIRTY.toString())
            }
        }

        ui.tietNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.updateNappyNote(s.toString())
            }

        })

        ui.btSave.setOnClickListener{
            viewModel.saveOrUpdateToDatabase()
            finish()
        }
    }

    private fun updateViewModelByIntent(viewModel: NappyRecordViewModel) {
        if (intent.hasExtra("id")) {
            val id = intent.getStringExtra("id")
            viewModel.updateID(id!!)
        }

        if (intent.hasExtra("nappyTime")) {
            val npyTime = intent.getStringExtra("nappyTime").toString()
            viewModel.updateNappyTime(npyTime)
        }
        if (intent.hasExtra("nappyCons")) {
            val npyCons = intent.getStringExtra("nappyCons").toString()
            viewModel.updateNappyCons(npyCons)
        }
        if (intent.hasExtra("nappyNote")) {
            val npyNote = intent.getStringExtra("nappyNote").toString()
            viewModel.updateNappyNote(npyNote)
        }
    }

    private fun showPage(viewModel : NappyRecordViewModel) {
        showNappyTime(viewModel)
        showNappyCons(viewModel)
        showFeedingNote(viewModel)
    }

    private fun showNappyTime(viewModel : NappyRecordViewModel) {
        ui.tvTimePickerCurrentTime.text = viewModel.nappyTime.value!!
    }

    private fun showNappyCons(viewModel : NappyRecordViewModel) {
        when(viewModel.nappyCons.value) {
            NappyCons.WET.toString() -> {
                ui.rbNappyConsWet.isChecked = true
                ui.rbNappyConsWetDirty.isChecked = false
            }
            else -> {
                ui.rbNappyConsWet.isChecked = false
                ui.rbNappyConsWetDirty.isChecked = true
            }
        }
    }

    private fun showFeedingNote(viewModel : NappyRecordViewModel) {
        ui.tietNote.setText(viewModel.nappyNote.value!!)
        ui.tietNote.setSelection(viewModel.nappyNote.value!!.length)
    }

}