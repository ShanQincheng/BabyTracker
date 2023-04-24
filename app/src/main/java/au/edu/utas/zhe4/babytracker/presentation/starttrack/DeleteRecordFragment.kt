package au.edu.utas.zhe4.babytracker.presentation.starttrack

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteRecordFragment(id: String, delete: (id: String) -> Unit) : DialogFragment() {
    private val id = id
    private val delete = delete

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Really want to delete the record?")
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { _, _ ->
                        delete(id)
                    })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
                })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}