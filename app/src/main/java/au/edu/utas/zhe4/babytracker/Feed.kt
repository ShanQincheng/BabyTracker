package au.edu.utas.zhe4.babytracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.edu.utas.zhe4.babytracker.databinding.ActivityFeedBinding

class Feed : AppCompatActivity() {
    private lateinit var ui : ActivityFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(ui.root)
    }
}