package com.moosphon.android.track

interface ITrackNode : IDataTrack {
    fun getParentNode(): ITrackNode?
    fun getHiredNode(): ITrackNode?
}