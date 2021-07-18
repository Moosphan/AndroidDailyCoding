package com.moosphon.android.daily.track.pages

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import com.moosphon.android.daily.R
import com.moosphon.android.daily.track.constant.TrackConstant
import com.moosphon.android.track.TrackArguments
import com.moosphon.android.track.api.IPageTrackNode
import com.moosphon.android.track.api.ITrackNode
import com.moosphon.android.track.getHiredTrackNode
import com.moosphon.android.track.trackEvent
import com.moosphon.android.track.trackModel

class TrackSampleDetailsActivity : AppCompatActivity(), IPageTrackNode {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_sample_details)
        initView()
    }

    private fun initView() {
        val posterView = findViewById<FrameLayout>(R.id.detailPosterContainer)
        val titleTV = findViewById<TextView>(R.id.detailGoodTitle)
        val typeTv = findViewById<TextView>(R.id.detailGoodType)
        val color = intent.getStringExtra("color")
        val title = intent.getStringExtra("name")
        val type = intent.getStringExtra("type")

        posterView.setBackgroundColor(Color.parseColor(color))
        titleTV.text = title
        typeTv.text = type

        titleTV.setOnClickListener {
//            titleTV.trackModel = this@TrackSampleDetailsActivity
            titleTV.trackEvent("detail_click")
        }
    }

    override fun getHiredMapping(): Map<String, String> {
        return TrackConstant.COMMON_HIRED_MAPPING
    }

    override fun getParentNode(): ITrackNode? {
        return null
    }

    override fun getHiredNode(): ITrackNode? {
        return intent.getHiredTrackNode()
    }

    override fun injectTrackArguments(arguments: TrackArguments) {
        arguments.putArgument(TrackConstant.KEY_PAGE_NAME, "Track Sample Detail")
    }
}