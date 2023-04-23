package au.edu.utas.zhe4.babytracker.presentation.record.feed

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class FeedRecordDatePickerFragment(viewModel: FeedRecordViewModel) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private var viewModel: FeedRecordViewModel = viewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        viewModel.setDate(year, month, day)
    }
}