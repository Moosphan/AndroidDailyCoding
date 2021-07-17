package com.moosphon.android.daily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.moosphon.android.daily.track.pages.TrackSampleListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.trackButton).setOnClickListener {
//            startActivity(Intent(this@MainActivity, TrackSampleListActivity::class.java))
        }
    }
}