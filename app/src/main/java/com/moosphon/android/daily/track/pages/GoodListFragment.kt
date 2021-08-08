package com.moosphon.android.daily.track.pages

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moosphon.android.daily.R
import com.moosphon.android.daily.base.PageState
import com.moosphon.android.daily.extension.argument
import com.moosphon.android.daily.track.bean.SimplePostItem
import com.moosphon.android.daily.track.constant.TrackConstant
import com.moosphon.android.daily.track.data.DataSourceGetter
import com.moosphon.android.track.TrackArguments
import com.moosphon.android.track.api.IDataTrack
import com.moosphon.android.track.api.ITrackNode
import com.moosphon.android.track.setHiredTrackNode
import com.moosphon.android.track.trackEvent
import com.moosphon.android.track.trackModel
import kotlin.properties.Delegates

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GoodListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoodListFragment : Fragment(), ITrackNode {
    private var param1: String? by argument()
    private var param2: String? by argument()

    private var mPageState: PageState by Delegates.observable(PageState.IDLE) {
        property, oldValue, newValue ->
        // TODO: handle differ states logic
    }

    private val dataList = DataSourceGetter.getGoodsListMockData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_good_list, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.goodsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)
        recyclerView.adapter = SimpleListAdapter()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GoodListFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GoodListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun getParentNode(): ITrackNode? {
        return requireActivity() as ITrackNode?
    }

    override fun getHiredNode(): ITrackNode? {
        return (requireActivity() as ITrackNode?)?.getHiredNode()
    }

    override fun injectTrackArguments(arguments: TrackArguments) {
        arguments.putArgument(TrackConstant.KEY_AREA_NAME, "goods shop")
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
}