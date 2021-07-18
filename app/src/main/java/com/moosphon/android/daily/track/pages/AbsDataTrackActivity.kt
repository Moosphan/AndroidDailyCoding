package com.moosphon.android.daily.track.pages

import androidx.appcompat.app.AppCompatActivity
import com.moosphon.android.daily.track.constant.TrackConstant
import com.moosphon.android.track.TrackArguments
import com.moosphon.android.track.api.IPageTrackNode
import com.moosphon.android.track.api.ITrackNode
import com.moosphon.android.track.getHiredTrackNode

abstract class AbsDataTrackActivity: AppCompatActivity(), IPageTrackNode {

    override fun getHiredMapping(): Map<String, String> {
        return TrackConstant.COMMON_HIRED_MAPPING
    }

    override fun getParentNode(): ITrackNode? {
        return null
    }

    override fun getHiredNode(): ITrackNode? {
        return intent.getHiredTrackNode()
    }

    override fun injectTrackArguments(arguments: TrackArguments) {
        // TODO: you can put track data here of page layer
    }
}