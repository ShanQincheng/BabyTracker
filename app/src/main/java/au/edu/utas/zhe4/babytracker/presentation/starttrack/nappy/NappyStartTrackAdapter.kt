package au.edu.utas.zhe4.babytracker.presentation.starttrack.nappy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.zhe4.babytracker.FEEDING_RECORD_INDEX
import au.edu.utas.zhe4.babytracker.databinding.RecyclerviewNappyRecordBinding
import au.edu.utas.zhe4.babytracker.domain.Nappy
import au.edu.utas.zhe4.babytracker.presentation.record.nappy.NappyRecordActivity
import au.edu.utas.zhe4.babytracker.presentation.starttrack.DeleteRecordFragment
import au.edu.utas.zhe4.babytracker.presentation.starttrack.sleep.SleepStartTrackActivity
import au.edu.utas.zhe4.babytracker.presentation.starttrack.sleep.SleepStartTrackViewModel
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString

class NappyStartTrackAdapter(
    private val records: MutableList<Nappy> = mutableListOf(),
    private val context: Context,
    private val viewModel: NappyStartTrackViewModel,
) :
    RecyclerView.Adapter<NappyStartTrackAdapter.ViewHolder>()
{

    class ViewHolder(val ui: RecyclerviewNappyRecordBinding) : RecyclerView.ViewHolder(ui.root) {
    }

    // Inflates a new row, and wraps it in a new ViewHolder( FeedingRecordHolder )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate a new row from the activity_feed_start_track.xml
        val ui = RecyclerviewNappyRecordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(ui)
    }

    // Populates each row data as required. First we get the data out of the array,
    // then we set the TextView in the row:
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.ui.tvNappyCons.text = record.cons.toString()
        holder.ui.tvNappyNote.text = record.note.toString()
        holder.ui.tvNappyChangeTime.text = LongToLocalDateTimeString(record.time!!)

        holder.ui.root.setOnClickListener {
            val i = Intent(holder.ui.root.context, NappyRecordActivity::class.java)
            i.putExtra(FEEDING_RECORD_INDEX, position)
            i.putExtra("id", record.id)
            i.putExtra("nappyTime", holder.ui.tvNappyChangeTime.text)
            i.putExtra("nappyCons", holder.ui.tvNappyCons.text)
            i.putExtra("nappyImg", record.image)
            i.putExtra("nappyNote", holder.ui.tvNappyNote.text)

            context.startActivity(i)
        }

        holder.itemView.setOnLongClickListener{
            val deleteDialog = DeleteRecordFragment(record.id!!, viewModel::deleteNappyRecord)
            deleteDialog.show((context as NappyStartTrackActivity).supportFragmentManager, "")

            true
        }
    }

    override fun getItemCount(): Int {
        return records.size
    }

    fun update(nappyRecords: MutableList<Nappy>) {
        val beforeSize = records.size

        records.clear()
        records.addAll(nappyRecords)

        notifyItemRangeRemoved(0, beforeSize)
        notifyItemRangeInserted(0, records.size)
    }
}