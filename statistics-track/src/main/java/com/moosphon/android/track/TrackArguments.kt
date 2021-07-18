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

    fun putAll(extras: Map<String, Any>) {
        trackArguments.putAll(extras)
    }

    fun getArguments(): Map<String, Any> {
        return trackArguments
    }

    fun removeArgument(key: String) {
        if (trackArguments.containsKey(key)) {
            trackArguments.remove(key)
        }
    }

    fun replaceArgumentKeyWithSameValue(newKey: String, oldKey: String, value: Any) {
        if (trackArguments.containsKey(oldKey)) {
            trackArguments.put(newKey, value)
            trackArguments.remove(oldKey)
        }
    }

    override fun toString(): String {
        return "TrackArgument: $trackArguments"
    }
}