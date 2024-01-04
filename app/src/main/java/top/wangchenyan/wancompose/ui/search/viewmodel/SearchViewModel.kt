package top.wangchenyan.wancompose.ui.search.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.wcy.wanandroid.compose.ui.search.HotKey
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.storage.HistoryPreferences

class SearchViewModel : ViewModel() {
    var keyword by mutableStateOf("")
    var hotKeys by mutableStateOf(listOf<HotKey>())
    var history by mutableStateOf(listOf<String>())

    init {
        getHotKey()
        getHistory()
    }

    private fun getHotKey() {
        viewModelScope.launch {
            val hotKeyRes = apiCall { Api.get().searchHotKey() }
            if (hotKeyRes.isSuccessWithData()) {
                hotKeys = hotKeyRes.data!!
            }
        }
    }

    private fun getHistory() {
        viewModelScope.launch {
            history = HistoryPreferences.getHistory()
        }
    }

    fun addHistory(item: String) {
        viewModelScope.launch {
            if (item.isEmpty() || history.contains(item)) {
                return@launch
            }
            history = history.toMutableList().apply { add(0, item) }
            HistoryPreferences.addHistory(item)
        }
    }

    fun removeHistory(item: String) {
        viewModelScope.launch {
            if (item.isEmpty() || !history.contains(item)) {
                return@launch
            }
            history = history.toMutableList().apply { remove(item) }
            HistoryPreferences.removeHistory(item)
        }
    }
}