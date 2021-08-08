package com.moosphon.android.daily.track.pages

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moosphon.android.daily.R
import com.moosphon.android.daily.extension.getRandomString
import com.moosphon.android.daily.track.bean.SimplePostItem
import com.moosphon.android.daily.track.constant.TrackConstant
import com.moosphon.android.daily.track.data.DataSourceGetter
import com.moosphon.android.daily.track.vm.GoodListViewModel
import com.moosphon.android.track.TrackArguments
import com.moosphon.android.track.api.IDataTrack
import com.moosphon.android.track.setHiredTrackNode
import com.moosphon.android.track.trackEvent
import com.moosphon.android.track.trackModel
import kotlinx.coroutines.flow.collect
import kotlin.properties.Delegates

/**
 * 模拟商品列表页面埋点场景
 * @author Moosphon
 * @date 2021/07/19
 */
class TrackSampleListActivity : AbsDataTrackActivity() {
    private val viewModel: GoodListViewModel by viewModels()
    private val mAdapter: SimpleListAdapter by lazy {
        SimpleListAdapter()
    }
    private val dataList = DataSourceGetter.getGoodsListMockData()
    private lateinit var currentFragment: Fragment
    private var tabIndex: Int by Delegates.observable(0) {
        property, oldValue, newValue ->
        currentFragment = when(newValue) {
            1 -> GoodListFragment.newInstance("good", "extra")
            else -> GoodListFragment.newInstance("", "")
        }
        // TODO: replace fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_sample_list)
        initView()
        lifecycleScope.launchWhenStarted {
            viewModel.goodIList.collect {
                Log.w(TAG, "refresh good list: $it")
                mAdapter.loadData(it)
            }
        }
    }

    private fun initView() {
        val recyclerView = findViewById<RecyclerView>(R.id.goodsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = mAdapter
        mAdapter.bindItemClickListener {
            val newType = getRandomString(6)
            Log.w(TAG, "change the good #${it.name} type to: $newType")
            viewModel.onGoodChange(it.id, newType)
        }
    }

    override fun injectTrackArguments(arguments: TrackArguments) {
        arguments.putArgument(TrackConstant.KEY_PAGE_NAME, "Home page")
    }

    inner class SimpleListAdapter: RecyclerView.Adapter<SimpleViewHolder>() {
        private lateinit var goodList: List<SimplePostItem>
        private var mClickListener: ((SimplePostItem) -> Unit)? = null

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
            holder.itemView.setOnClickListener {
                mClickListener?.invoke(goodList[position])
            }
            holder.bind(goodList[position])
        }

        override fun getItemCount(): Int {
            return goodList.size
        }

        fun loadData(data: List<SimplePostItem>) {
            goodList = data
            notifyDataSetChanged()
        }

        fun bindItemClickListener(listener: (SimplePostItem) -> Unit) {
            this.mClickListener = listener
        }

    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), IDataTrack {
        private var postTextView: TextView? = null
        private var postImage: FrameLayout? = null
        private var postInfo: SimplePostItem? = null
        private var postTypeText: TextView? = null

        init {
            postTextView = itemView.findViewById(R.id.postTitle)
            postImage = itemView.findViewById(R.id.poster)
            postTypeText = itemView.findViewById(R.id.postType)
        }

        fun bind(data: SimplePostItem) {
            postInfo = data
            this.itemView.trackModel = this
            postImage?.setBackgroundColor(Color.parseColor(data.color))
            postTextView?.setText(data.name)
            postTypeText?.setText(data.type)

            /*暂时关闭埋点*/
//            itemView.setOnClickListener {
//                itemView.trackEvent("click_detail")
//                val context = itemView.context
//                val intent = Intent(context, TrackSampleDetailsActivity::class.java)
//                intent.setHiredTrackNode(it)
//                intent.putExtra("color", postInfo!!.color)
//                intent.putExtra("name", postInfo!!.name)
//                intent.putExtra("type", postInfo!!.type)
//                context.startActivity(intent)
//            }
//
//            postImage?.setOnClickListener {
//                postImage!!.trackEvent("click_poster")
//            }
        }

        override fun injectTrackArguments(arguments: TrackArguments) {
            // put track data
            arguments.putArgument(TrackConstant.KEY_AREA_NAME, "Goods list")
            arguments.putArgument(TrackConstant.KEY_POST_ID, postInfo!!.id)
            arguments.putArgument(TrackConstant.KEY_POST_TYPE, postInfo!!.type)
        }

    }

    companion object {
        const val TAG = "TrackSampleListActivity"
    }
}