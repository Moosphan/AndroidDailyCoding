package com.moosphon.android.track.api

interface ITrackNode : IDataTrack {
    /**
     * Returns parent node like #Activity is parent node of Fragment.
     */
    fun getParentNode(): ITrackNode?

    /**
     * Returns hired node like ListPage is previous page of details page.
     * Used to transfer track data of pages.
     */
    fun getHiredNode(): ITrackNode?
}