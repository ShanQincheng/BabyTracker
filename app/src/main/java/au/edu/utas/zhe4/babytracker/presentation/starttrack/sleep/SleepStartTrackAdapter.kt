package au.edu.utas.zhe4.babytracker.presentation.starttrack.sleep

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.zhe4.babytracker.databinding.RecyclerviewSleepRecordBinding
import au.edu.utas.zhe4.babytracker.domain.Sleep
import au.edu.utas.zhe4.babytracker.presentation.record.sleep.SleepRecordActivity
import au.edu.utas.zhe4.babytracker.presentation.starttrack.DeleteRecordFragment

class SleepStartTrackAdapter(
    private val records: MutableList<Sleep> = mutableListOf(),
    private val context: Context,
    private val viewModel: SleepStartTrackViewModel,
) :
    RecyclerView.Adapter<SleepStartTrackAdapter.ViewHolder>()
{

    class ViewHolder(val ui: RecyclerviewSleepRecordBinding) : RecyclerView.ViewHolder(ui.root){
    }

    // Inflates a new row, and wraps it in a new ViewHolder( FeedingRecordHolder )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate a new row from the activity_feed_start_track.xml
        val ui = RecyclerviewSleepRecordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(ui)
    }

    // Populates each row data as required. First we get the data out of the array,
    // then we set the TextView in the row:
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.ui.tvStartTime.text = record.startTime.toString()
        holder.ui.tvEndTime.text = record.endTime.toString()
        holder.ui.tvNappyNote.text = record.note.toString()

        holder.ui.root.setOnClickListener {
            val i = Intent(holder.ui.root.context, SleepRecordActivity::class.java)
            i.putExtra("id", record.id)
            i.putExtra("sleepTime", record.time)
            i.putExtra("sleepStartTime", record.startTime)
            i.putExtra("sleepEndTime", record.endTime)
            i.putExtra("sleepNote", record.note)

            context.startActivity(i)
        }

        holder.itemView.setOnLongClickListener{
            val deleteDialog = DeleteRecordFragment(record.id!!, viewModel::deleteSleepRecord)
            deleteDialog.show((context as SleepStartTrackActivity).supportFragmentManager, "")

            true
        }

    }

    override fun getItemCount(): Int {
        return records.size
    }

    fun update(sleepRecords: MutableList<Sleep>) {
        val beforeSize = records.size

        records.clear()
        records.addAll(sleepRecords)

        notifyItemRangeRemoved(0, beforeSize)
        notifyItemRangeInserted(0, records.size)
    }
}
