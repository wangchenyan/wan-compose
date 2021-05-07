package me.wcy.wanandroid.compose.ui.square.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.wcy.wanandroid.compose.api.Api
import me.wcy.wanandroid.compose.api.apiCall
import me.wcy.wanandroid.compose.ui.home.model.Article
import me.wcy.wanandroid.compose.widget.LoadState

/**
 * Created by wcy on 2021/4/1.
 */
class SquareViewModel : ViewModel() {
    var state by mutableStateOf(LoadState.LOADING)
    var list by mutableStateOf(mutableListOf<Article>())

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            val articleList = apiCall { Api.get().getSquareArticleList() }
            if (articleList.isSuccess()) {
                state = LoadState.SUCCESS
                list = list.apply {
                    clear()
                    addAll(articleList.data!!.datas)
                }
            } else {
                state = LoadState.FAIL
            }
        }
    }
}