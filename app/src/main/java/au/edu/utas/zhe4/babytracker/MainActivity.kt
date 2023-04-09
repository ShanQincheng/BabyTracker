package au.edu.utas.zhe4.babytracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import au.edu.utas.zhe4.babytracker.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import au.edu.utas.zhe4.babytracker.entities.Feed


class MainActivity : AppCompatActivity() {
    private lateinit var ui : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.cvFeed.setOnClickListener {
            val i = Intent(ui.root.context, FeedStartTrack::class.java)
            startActivity(i)
        }

        //get db connection
        val db = Firebase.firestore
        Log.d("FIREBASE", "Firebase connected: ${db.app.name}")
    }
}