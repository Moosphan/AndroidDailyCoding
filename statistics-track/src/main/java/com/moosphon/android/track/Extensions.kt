package com.moosphon.android.track

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.moosphon.android.track.R.id.view_track_common_id
import com.moosphon.android.track.api.IDataTrack
import com.moosphon.android.track.api.IPageTrackNode
import com.moosphon.android.track.api.ITrackNode
import com.moosphon.android.track.logger.TrackLogger
import com.moosphon.android.track.util.TrackCacheUtil
import java.io.Serializable
import java.util.*

val TAG_TRACK_ID = view_track_common_id

var View.trackModel: IDataTrack?
    get() = this.getTag(TAG_TRACK_ID) as? IDataTrack
    set(value) {
        this.setTag(TAG_TRACK_ID, value)
    }

// TODO：由View触发，但注入的埋点数据并非都是view，还有ViewHolder、Fragment等
// 借助于View与生俱来的责任链机制实埋点链路的数据收集
// 注意区分View组件与非View组件
fun View.trackEvent(event: String) {

    fun isRootView(parent: View): Boolean {
        val parentViewName = parent::class.qualifiedName
        if (TextUtils.equals(parentViewName, "com.android.internal.policy.DecorView")) {
            return true
        }
        return false
    }

    TrackLogger.INSTANCE.logTrackStart()
    // need to collect arguments and upload
    val trackArguments = TrackArguments()
    this.trackModel?.injectTrackArguments(trackArguments)

    var tempView: View = this
    while (tempView.parent != null) {
        Log.d("DataTrackLogger", "has child, target view: [${tempView.parent::class.qualifiedName}]")
        if (isRootView(tempView)) {
            Log.d("DataTrackLogger", "find the root view node, finish")
            break
        }
        tempView = tempView.parent as ViewGroup
        if (tempView.trackModel != null) {
            tempView.trackModel?.injectTrackArguments(trackArguments)
        }
    }
    //TODO: adapt for fragment
    val contextPage = this.context
    if (contextPage != null && (contextPage is IPageTrackNode)) {
        Log.d("DataTrackLogger", "find the page track node.")
        // handle hired track extras
        if (contextPage.getHiredNode() != null) {
            Log.d("DataTrackLogger", "has hired node.")
            // transfer arguments by hired mapping
            val mapping = contextPage.getHiredMapping()
            contextPage.getHiredNode()?.injectTrackArguments(trackArguments)
            mapping?.forEach {
                trackArguments.replaceArgumentKeyWithSameValue(it.value, it.key, trackArguments.getArguments()[it.key]!!)
            }
        }
        // type of page track model
        contextPage.injectTrackArguments(trackArguments)
        if (contextPage.getParentNode() != null) {
            contextPage.getParentNode()?.injectTrackArguments(trackArguments)
        }
    }
    this.trackExtrasMap = trackArguments.getArguments()
    TrackLogger.INSTANCE.logTrackEnd()
    Log.d("DataTrackLogger", "track success, event: $event, arguments: $trackExtrasMap")
}

var View.trackExtrasMap: Map<String, Any>?
    get() = TrackCacheUtil.getTrackDataExtras()
    set(value) {
        TrackCacheUtil.setTrackExtrasMap(value)
    }

fun Intent.setHiredTrackNode(view: View) {
    if (view.trackExtrasMap != null) {
        Log.d("DataTrackLogger", "setHiredTrackNode, data: ${view.trackExtrasMap.toString()}")
        this.putExtra("trackNode", view.trackExtrasMap as Serializable)
    }
}

fun Intent.getHiredTrackNode(): ITrackNode? {
    val previousExtras: HashMap<String, *> = this.getSerializableExtra("trackNode") as HashMap<String, *>?
        ?: return null
    return object : ITrackNode {
        override fun getParentNode(): ITrackNode? {
            return null
        }

        override fun getHiredNode(): ITrackNode? {
            return null
        }

        override fun injectTrackArguments(arguments: TrackArguments) {
            arguments.putAll(previousExtras)
        }

    }
}