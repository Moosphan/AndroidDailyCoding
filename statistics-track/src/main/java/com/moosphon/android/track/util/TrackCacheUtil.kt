package com.moosphon.android.track.util

object TrackCacheUtil {
    private var cacheMap: Map<String, Any>? = null

    fun setTrackExtrasMap(extras: Map<String, Any>?) {
        this.cacheMap = extras
    }

    fun getTrackDataExtras(): Map<String, Any>? {
        return cacheMap
    }
}