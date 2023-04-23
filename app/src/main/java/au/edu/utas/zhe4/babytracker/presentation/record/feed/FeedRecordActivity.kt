package au.edu.utas.zhe4.babytracker.presentation.record.feed

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import au.edu.utas.zhe4.babytracker.FEEDING_RECORD_INDEX
import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedBinding
import au.edu.utas.zhe4.babytracker.domain.FeedingSide
import au.edu.utas.zhe4.babytracker.domain.FeedingType
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModelFactory

class FeedRecordActivity : AppCompatActivity() {
    private lateinit var ui : ActivityFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val viewModel = ViewModelProvider(
            this,
            BabyTrackerViewModelFactory
        )[FeedRecordViewModel::class.java]

        updateViewModelByIntent(viewModel)

        showPage(viewModel)

        viewModel.feedingTime.observe(this, Observer {
            showFeedingTime(viewModel)
        })

        viewModel.feedingDuration.observe(this, Observer {
            showFeedingDuration(viewModel)
        })

        ui.rgFeedType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                ui.rbBreastfeeding.id ->
                    viewModel.updateFeedingType(FeedingType.BREASTFEEDING.toString())
                else ->
                    viewModel.updateFeedingType(FeedingType.BOTTLE.toString())
            }
        }

        ui.btTimePickerPopUp.setOnClickListener {
            val timePicker = FeedRecordTimePickerFragment(viewModel)
            timePicker.show(supportFragmentManager, "timePicker")

            val datePicker = FeedRecordDatePickerFragment(viewModel)
            datePicker.show(supportFragmentManager, "datePicker")
        }

        ui.rgFeedSide.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                ui.rbFeedSideLeft.id ->
                    viewModel.updateFeedingSide(FeedingSide.LEFT.toString())
                else ->
                    viewModel.updateFeedingSide(FeedingSide.RIGHT.toString())
            }
        }

        ui.btDurationPickerPopUp.setOnClickListener {
            val numberPicker = FeedRecordNumberPicker(this, viewModel)
            numberPicker.showNumberPickerDialog()
        }

        ui.tietNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.updateFeedingNote(s.toString())
            }

        })

        ui.btSave.setOnClickListener{
            viewModel.saveOrUpdateToDatabase()
            finish()
        }
    }

    private fun updateViewModelByIntent(viewModel: FeedRecordViewModel) {
        if (intent.hasExtra(FEEDING_RECORD_INDEX)) {
            val id = intent.getIntExtra(FEEDING_RECORD_INDEX, -1)
            viewModel.updateID(id)
        }

        if (intent.hasExtra("feedingType")) {
            val fType = intent.getStringExtra("feedingType").toString()
            viewModel.updateFeedingType(fType)
        }
        if (intent.hasExtra("feedingTime")) {
            val fTime = intent.getStringExtra("feedingTime").toString()
            viewModel.updateFeedingTime(fTime)
        }
        if (intent.hasExtra("feedingSide")) {
            val fSide = intent.getStringExtra("feedingSide").toString()
            viewModel.updateFeedingSide(fSide)
        }
        if (intent.hasExtra("feedingDuration")) {
            val fDuration = intent.getStringExtra("feedingDuration").toString()
            viewModel.updateFeedingDuration(fDuration)
        }
        if (intent.hasExtra("feedingNote")) {
            val fNote = intent.getStringExtra("feedingNote").toString()
            viewModel.updateFeedingNote(fNote)
        }
    }

    private fun showPage(viewModel : FeedRecordViewModel) {
        showFeedingType(viewModel)
        showFeedingTime(viewModel)
        showFeedingSide(viewModel)
        showFeedingDuration(viewModel)
        showFeedingNote(viewModel)
    }

    private fun showFeedingType(viewModel : FeedRecordViewModel) {
        when(viewModel.feedingType.value) {
            FeedingType.BREASTFEEDING.toString() -> {
                ui.rbBreastfeeding.isChecked = true
                ui.rbBottleFeed.isChecked = false
            }
            else -> {
                ui.rbBreastfeeding.isChecked = false
                ui.rbBottleFeed.isChecked = true
            }
        }
    }

    private fun showFeedingTime(viewModel : FeedRecordViewModel) {
        ui.tvTimePickerCurrentTime.text = viewModel.feedingTime.value!!
    }

    private fun showFeedingSide(viewModel : FeedRecordViewModel) {
        when(viewModel.feedingSide.value) {
            FeedingSide.LEFT.toString() -> {
                ui.rbFeedSideLeft.isChecked = true
                ui.rbFeedSideRight.isChecked = false
            }
            else -> {
                ui.rbFeedSideLeft.isChecked = false
                ui.rbFeedSideRight.isChecked = true
            }
        }
    }

    private fun showFeedingDuration(viewModel : FeedRecordViewModel) {
        ui.tvDuration.text = viewModel.feedingDuration.value!!
    }

    private fun showFeedingNote(viewModel : FeedRecordViewModel) {
        ui.tietNote.setText(viewModel.feedingNote.value!!)
        ui.tietNote.setSelection(viewModel.feedingNote.value!!.length)
    }

}