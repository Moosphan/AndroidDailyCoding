package com.moosphon.android.track

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.moosphon.android.track.R.id.view_track_common_id

val TAG_TRACK_ID = view_track_common_id

var View.trackModel: IDataTrack?
    get() = this.getTag(TAG_TRACK_ID) as? IDataTrack
    set(value) {
        this.setTag(TAG_TRACK_ID, value)
    }

//TODO
fun View.trackEvent(event: String) {
    fun traverseTrackInViewTree(view: View?, arguments: TrackArguments) {
        if (view == null || view !is ViewGroup) return
        Log.d("DataTrackLogger", "target view: ${view::class.java}")
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
    Log.d("DataTrackLogger", "start to track data")
    // need to collect arguments and upload
    val trackArguments = TrackArguments()
    if (this is IDataTrack) {
        this.trackModel?.injectTrackArguments(trackArguments)
    }
    var tempView: View = this
    while (tempView.parent != null) {
        tempView = this.parent as ViewGroup
        traverseTrackInViewTree(tempView, trackArguments)
    }
    if (this.parent == null) {
        val contextPage = this.context
        if (contextPage != null && (contextPage is ITrackNode)) {
            // type of page track model
            contextPage.injectTrackArguments(trackArguments)
            if (contextPage.getParentNode() != null) {
                contextPage.getParentNode()?.injectTrackArguments(trackArguments)
            }
        }
    }
    Log.d("DataTrackLogger", "event: $event, arguments: $trackArguments")
}

fun Intent.setHiredTrackNode(node: ITrackNode) {
}