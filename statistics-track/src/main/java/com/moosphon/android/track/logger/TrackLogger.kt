package com.moosphon.android.track.logger

import android.util.Log

class TrackLogger private constructor() {
    companion object {
        const val TAG = "TrackLogger"
        val INSTANCE: TrackLogger by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            TrackLogger()
        }
    }

    private var logLevel: Int = Log.DEBUG
    private var startTime = System.currentTimeMillis()

    fun setLogLevel(level: Int) {
        this.logLevel = level
    }

    fun logTrackStart() {
        startTime = System.currentTimeMillis()
//        Log.d(TAG, "start to track at time >> $startTime")
        Log.println(logLevel, TAG, "start to track at time >> $startTime")
    }

    fun logTrackEnd() {
        //Log.d(TAG, "end to track with time >> ${System.currentTimeMillis() - startTime}")
        Log.println(logLevel, TAG, "end to track with time >> ${System.currentTimeMillis() - startTime} ms")
    }
}