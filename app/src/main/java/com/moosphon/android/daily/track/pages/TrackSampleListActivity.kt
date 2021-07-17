package com.moosphon.android.daily.track.pages

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moosphon.android.daily.R
import com.moosphon.android.daily.track.constant.TrackConstant
import com.moosphon.android.track.*

class TrackSampleListActivity : AppCompatActivity(), ITrackNode {
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
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SimpleListAdapter()
    }

    override fun getParentNode(): ITrackNode? {
        return null
    }

    override fun getHiredNode(): ITrackNode? {
        return null
    }

    override fun injectTrackArguments(arguments: TrackArguments) {
        arguments.putArgument(TrackConstant.KEY_PAGE_NAME, "TrackSampleList")
    }

    inner class SimpleListAdapter: RecyclerView.Adapter<SimpleViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
            return SimpleViewHolder(View.inflate(this@TrackSampleListActivity, R.layout.track_item_list_posts, null))
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
        private var postImage: View? = null
        private var postInfo: SimplePostItem? = null

        init {
            postTextView = itemView.findViewById(R.id.postTitle)
            postImage = itemView.findViewById(R.id.poster)
        }

        fun bind(data: SimplePostItem) {
            postInfo = data
            itemView.trackModel = this
            postImage?.setBackgroundColor(Color.parseColor(data.color))
            postTextView?.setText(data.name)

            itemView.setOnClickListener {
                itemView.trackEvent("click_detail")
//                val context = itemView.context
//                val intent = Intent(context, TrackSampleDetailsActivity::class.java)
//                context.startActivity(intent)

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