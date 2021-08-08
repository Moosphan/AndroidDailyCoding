package com.moosphon.android.daily.track.repository

import com.moosphon.android.daily.track.bean.SimplePostItem
import com.moosphon.android.daily.track.data.DataSourceGetter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GoodListRepository : IGoodListRepository {
    override fun fetchGoodList(): Flow<List<SimplePostItem>> {
        return flow { emit(DataSourceGetter.getGoodsListMockData().asList()) }.flowOn(Dispatchers.IO)
    }
}