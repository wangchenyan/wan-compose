package top.wangchenyan.wancompose.ui.wechat.viewmodel

import android.util.LongSparseArray
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.ui.wechat.model.WeChatAuthor
import top.wangchenyan.wancompose.widget.LoadState

class WeChatViewModel : ViewModel() {
    var pageState by mutableStateOf(LoadState.LOADING)
    var authorList by mutableStateOf(listOf<WeChatAuthor>())
    val tabViewModelMap = LongSparseArray<WeChatTabViewModel>()

    init {
        getAuthorList()
    }

    fun getAuthorList() {
        viewModelScope.launch {
            pageState = LoadState.LOADING
            val authorListRes = apiCall { Api.get().getWeChatAuthorList() }
            if (authorListRes.isSuccessWithData()) {
                pageState = LoadState.SUCCESS
                authorList = authorListRes.data!!
            } else {
                pageState = LoadState.FAIL
            }
        }
    }
}