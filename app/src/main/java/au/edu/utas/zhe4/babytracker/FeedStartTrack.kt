package au.edu.utas.zhe4.babytracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedStartTrackBinding

class FeedStartTrack : AppCompatActivity() {

    private lateinit var ui : ActivityFeedStartTrackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityFeedStartTrackBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.btStartTrack.setOnClickListener{
            val i = Intent(ui.root.context, Feed::class.java)
            startActivity(i)
        }
    }
}