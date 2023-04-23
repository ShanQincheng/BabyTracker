package au.edu.utas.zhe4.babytracker.presentation.record.feed

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class FeedRecordTimePickerFragment(viewModel: FeedRecordViewModel) : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var viewModel: FeedRecordViewModel = viewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)


        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        viewModel.setTime(hourOfDay, minute)
    }
}