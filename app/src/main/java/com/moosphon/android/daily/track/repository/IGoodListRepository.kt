package com.moosphon.android.daily.track.repository

import com.moosphon.android.daily.track.bean.SimplePostItem
import kotlinx.coroutines.flow.Flow

interface IGoodListRepository {
    fun fetchGoodList(): Flow<List<SimplePostItem>>
}