package au.edu.utas.zhe4.babytracker.presentation.record.feed

import android.app.AlertDialog
import android.content.Context
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment

class FeedRecordNumberPicker(ctx: Context, viewModel: FeedRecordViewModel)  : DialogFragment() {
    private val context: Context = ctx
    private val viewModel = viewModel

    fun showNumberPickerDialog() {
        val numberPicker = NumberPicker(context)
        numberPicker.minValue = 0 // Set minimum value
        numberPicker.maxValue = 200 // Set maximum value

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose duration")
        builder.setView(numberPicker)
        builder.setPositiveButton("OK") { _, _ ->
            val selectedNumber = numberPicker.value
            viewModel.updateFeedingDuration(selectedNumber.toString())
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
}