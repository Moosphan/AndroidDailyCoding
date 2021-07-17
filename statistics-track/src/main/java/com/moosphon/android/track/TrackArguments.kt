package com.moosphon.android.track

import android.util.Log
import java.util.*

/**
 * Params of data track chain.
 * @author moosphon
 * @date 2021/07/16
 */
class TrackArguments {
    companion object {
        const val TAG = "TrackArguments"
    }
    private var trackArguments = HashMap<String, Any>()

    fun putArgument(key: String, value: Any) {
        if (trackArguments.containsKey(key)) {
            Log.w(TAG, "already has this track param, do you confirm to overwrite?")
        }
        trackArguments.put(key, value)
    }

    fun getArguments(): Map<String, Any> {
        return trackArguments
    }

    override fun toString(): String {
        return "TrackArgument: $trackArguments"
    }
}