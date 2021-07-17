package com.moosphon.android.track

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.viewpager.widget.ViewPager
import com.moosphon.android.track.R.id.view_track_common_id

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
    fun traverseTrackInViewTree(view: View?, arguments: TrackArguments) {
        if (view == null || view !is ViewGroup) return
        Log.d("DataTrackLogger", "target view: ${view::class}")
        view.forEach {
            if (it is IDataTrack) {
                Log.d("DataTrackLogger", "find the track model")
                it.injectTrackArguments(arguments)
            }
            if (it is ViewGroup) {
                Log.d("DataTrackLogger", "continue to track: ${it.childCount}")
                traverseTrackInViewTree(it, arguments)
            }
        }
    }

    fun isRootView(parent: View): Boolean {
        val parentViewName = parent::class.qualifiedName
        if (TextUtils.equals(parentViewName, "com.android.internal.policy.DecorView")) {
            return true
        }
        return false
    }

    Log.d("DataTrackLogger", "start to track data")
    // need to collect arguments and upload
    val trackArguments = TrackArguments()
    if (this is IDataTrack) {
        this.trackModel?.injectTrackArguments(trackArguments)
    }

    var tempView: View = this
    while (tempView.parent != null) {
        Log.d("DataTrackLogger", "has child, target view: ${tempView.parent::class.qualifiedName}")
        if (isRootView(tempView)) {
            Log.d("DataTrackLogger", "find the root view node, finish")
            break
        }
        tempView = tempView.parent as ViewGroup
        Log.d("DataTrackLogger", "before track model: ${tempView.trackModel}")
        if (tempView is IDataTrack) {
            Log.d("DataTrackLogger", "find the track model: ${tempView.trackModel}")
            tempView.injectTrackArguments(trackArguments)
        }
    }
    val contextPage = this.context
    if (contextPage != null && (contextPage is ITrackNode)) {
        // type of page track model
        contextPage.injectTrackArguments(trackArguments)
        if (contextPage.getParentNode() != null) {
            contextPage.getParentNode()?.injectTrackArguments(trackArguments)
        }
    }
    Log.d("DataTrackLogger", "event: $event, arguments: $trackArguments")
}

fun Intent.setHiredTrackNode(node: IDataTrack) {
}