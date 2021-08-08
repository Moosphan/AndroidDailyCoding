package com.moosphon.android.cellpacking.library

import android.content.Context
import android.content.pm.PackageManager

object ChannelReader {

    /**
     * 从AndroidManifest.xml中读取metadata的渠道信息
     */
    fun getChannelFromMetadata(context: Context): String {
        try {
            val packageManager = context.packageManager
            val applicationInfo = packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            return applicationInfo.metaData.getString("channel") ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }
}