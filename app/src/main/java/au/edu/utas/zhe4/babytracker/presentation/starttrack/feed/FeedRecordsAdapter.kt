package au.edu.utas.zhe4.babytracker.presentation.starttrack.feed

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.zhe4.babytracker.FEEDING_RECORD_INDEX
import au.edu.utas.zhe4.babytracker.databinding.FeedRecordBinding
import au.edu.utas.zhe4.babytracker.entities.Feed
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString

class FeedRecordsAdapter(
    private val records: MutableList<Feed>,
    private val context: Context,
) :
    RecyclerView.Adapter<FeedRecordsAdapter.ViewHolder>()
{

    class ViewHolder(val ui: FeedRecordBinding) : RecyclerView.ViewHolder(ui.root) {
        val feedingTypeView: TextView = ui.tvFeedingType
        val feedingSideView: TextView = ui.tvFeedingSide
        val feedingNote: TextView = ui.tvFeedingNote
        val feedingTime: TextView = ui.tvFeedingTime
    }

    // Inflates a new row, and wraps it in a new ViewHolder( FeedingRecordHolder )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate a new row from the activity_feed_start_track.xml
        val ui = FeedRecordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(ui)
    }

    // Populates each row data as required. First we get the data out of the array,
    // then we set the TextView in the row:
    override fun onBindViewHolder(holder: FeedRecordsAdapter.ViewHolder, position: Int) {
        val record = records[position]
        holder.ui.tvFeedingType.text = record.type.toString()
        holder.ui.tvFeedingSide.text = record.side.toString()
        holder.ui.tvFeedingNote.text = record.note.toString()
        holder.ui.tvFeedingTime.text = LongToLocalDateTimeString(record.time!!)

        holder.ui.root.setOnClickListener {
            val i = Intent(holder.ui.root.context,
                au.edu.utas.zhe4.babytracker.Feed::class.java)
            i.putExtra(FEEDING_RECORD_INDEX, position)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return records.size
    }
}