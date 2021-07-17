package com.moosphon.android.track

/**
 * Track node to realize page data tracking.
 * @author moosphon
 * @date 2021/07/16
 */
interface IPageTrackNode: ITrackNode {
    /**
     * 获取当前节点与雇佣节点的参数映射表
     */
    fun getHiredMapping(): Map<String, String>
}