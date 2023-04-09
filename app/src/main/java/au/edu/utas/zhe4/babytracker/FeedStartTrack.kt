package au.edu.utas.zhe4.babytracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedStartTrackBinding
import au.edu.utas.zhe4.babytracker.databinding.FeedRecordBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import au.edu.utas.zhe4.babytracker.entities.Feed

class FeedStartTrack : AppCompatActivity() {

    private lateinit var ui : ActivityFeedStartTrackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityFeedStartTrackBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val db = Firebase.firestore
        val feedingCollection = db.collection("feedings")

        val feedingRecords = mutableListOf<Feed>()
        //get all feeding records
        feedingCollection
            .get()
            .addOnSuccessListener { result ->
                //this line clears the list, and prevents a bug where items
                // would be duplicated upon rotation of screen
                feedingRecords.clear()
                Log.d(FIREBASE_TAG, "--- all feeding records ---")
                for (document in result)
                {
                    Log.d(FIREBASE_TAG, document.toString())
                    val fRecord = document.toObject<Feed>()
                    fRecord.id = document.id
                    Log.d(FIREBASE_TAG, fRecord.toString())

                    feedingRecords.add(fRecord)
                }

                //you may choose to fix the warning that notifyDataSetChanged() is not specific enough using:
                (ui.rvThisWeek.adapter as FeedingRecordsAdapter).
                    notifyItemRangeInserted(0, feedingRecords.size)
            }

        ui.btStartTrack.setOnClickListener{
            val i = Intent(ui.root.context, au.edu.utas.zhe4.babytracker.Feed::class.java)
            startActivity(i)
        }

        ui.rvThisWeek.adapter = FeedingRecordsAdapter(records = feedingRecords)

        // tell RecyclerView how to display the items
        ui.rvThisWeek.layoutManager = LinearLayoutManager(this).
            apply { orientation = LinearLayoutManager.HORIZONTAL }
    }

    // A class that stores references to the View layout of our activity_feed_start_track.xml
    inner class FeedingRecordHolder(
        var ui: FeedRecordBinding,
    ) : RecyclerView.ViewHolder(
        ui.root,
    ) {

    }

    // The adapter is the controller that handles the communication between our model and our view
    inner class FeedingRecordsAdapter(
        private val records: MutableList<Feed>,
    ) : RecyclerView.Adapter<FeedingRecordHolder>()
    {
        // Inflates a new row, and wraps it in a new ViewHolder( FeedingRecordHolder )
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedingRecordHolder {
            //inflate a new row from the activity_feed_start_track.xml
            val ui = FeedRecordBinding.inflate(
                layoutInflater, parent, false)
            return FeedingRecordHolder(ui)
        }

        // Populates each row data as required. First we get the data out of the array,
        // then we set the TextView in the row:
        override fun onBindViewHolder(holder: FeedingRecordHolder, position: Int) {
            val record = records[position]
            holder.ui.tvFeedingType.text = record.side.toString()
            holder.ui.tvFeedingNote.text = record.note.toString()
        }

        override fun getItemCount(): Int {
            return records.size
        }
    }
}