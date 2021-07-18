package com.moosphon.android.daily.track.pages

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moosphon.android.daily.R
import com.moosphon.android.daily.track.constant.TrackConstant
import com.moosphon.android.track.TrackArguments
import com.moosphon.android.track.api.IDataTrack
import com.moosphon.android.track.setHiredTrackNode
import com.moosphon.android.track.trackEvent
import com.moosphon.android.track.trackModel


class TrackSampleListActivity : AbsDataTrackActivity() {
    private val dataList = arrayOf(
        SimplePostItem(
            "毛衣",
            "#ffee67",
            "12671571",
            "clothes"
        ),
        SimplePostItem(
            "论自由民主专政",
            "#96e467",
            "12632572",
            "books"
        ),
        SimplePostItem(
            "拖鞋",
            "#e5ffe5",
            "126736730",
            "clothes"
        ),
        SimplePostItem(
            "MI 10",
            "#78ee67",
            "12671511",
            "device"
        ),
        SimplePostItem(
            "钢铁是怎样炼成的",
            "#6f45e8",
            "12124513",
            "books"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_sample_list)
        initView()
    }

    private fun initView() {
        val recyclerView = findViewById<RecyclerView>(R.id.goodsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = SimpleListAdapter()
    }

    override fun injectTrackArguments(arguments: TrackArguments) {
        arguments.putArgument(TrackConstant.KEY_PAGE_NAME, "Track Sample List")
    }

    inner class SimpleListAdapter: RecyclerView.Adapter<SimpleViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
            return SimpleViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.track_item_list_posts,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
            holder.bind(dataList[position])
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), IDataTrack {
        private var postTextView: TextView? = null
        private var postImage: FrameLayout? = null
        private var postInfo: SimplePostItem? = null

        init {
            postTextView = itemView.findViewById(R.id.postTitle)
            postImage = itemView.findViewById(R.id.poster)
        }

        fun bind(data: SimplePostItem) {
            postInfo = data
            this.itemView.trackModel = this
            postImage?.setBackgroundColor(Color.parseColor(data.color))
            postTextView?.setText(data.name)

            itemView.setOnClickListener {
                itemView.trackEvent("click_detail")
                val context = itemView.context
                val intent = Intent(context, TrackSampleDetailsActivity::class.java)
                intent.setHiredTrackNode(it)
                intent.putExtra("color", postInfo!!.color)
                intent.putExtra("name", postInfo!!.name)
                intent.putExtra("type", postInfo!!.type)
                context.startActivity(intent)
            }

            postImage?.setOnClickListener {
                postImage!!.trackEvent("click_poster")
            }
        }

        override fun injectTrackArguments(arguments: TrackArguments) {
            // put track data
            arguments.putArgument(TrackConstant.KEY_AREA_NAME, "Goods list")
            arguments.putArgument(TrackConstant.KEY_POST_ID, postInfo!!.id)
            arguments.putArgument(TrackConstant.KEY_POST_TYPE, postInfo!!.type)
        }

    }

    data class SimplePostItem(
        var name: String,
        var color: String,
        var id: String,
        var type: String
    )
}