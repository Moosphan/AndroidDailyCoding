package com.moosphon.android.daily.track.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moosphon.android.daily.track.bean.SimplePostItem
import com.moosphon.android.daily.track.data.DataSourceGetter
import com.moosphon.android.daily.track.repository.GoodListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * 商品列表的 ViewModel layer。
 * @author moosphon
 * Date time: 2021/8/8 10:08 上午
 */
class GoodListViewModel: ViewModel() {
    private val _goodList = MutableStateFlow<List<SimplePostItem>>(emptyList())

    val goodIList: StateFlow<List<SimplePostItem>> = _goodList

    private val repository: GoodListRepository = GoodListRepository()

    init {
        viewModelScope.launch {
            repository.fetchGoodList().collect {
                _goodList.emit(it)
            }
        }
    }

    fun onGoodChange(id: String, type: String) {
        val updateList = deepCopyList(_goodList.value)
        updateList.find { id == it.id }?.let {
            it.type = type
        }
        Log.e("GoodListViewModel", "old: ${_goodList.value}, new: $updateList")
        Log.e("GoodListViewModel", "old data equals with new data: ${_goodList.value == updateList}")
        _goodList.value = updateList
    }

    private fun deepCopyList(source: List<SimplePostItem>): List<SimplePostItem> {
        val outputList = mutableListOf<SimplePostItem>()
        source.forEach {
            outputList.add(it.copy())
        }
        return outputList
    }
}