package top.wangchenyan.wancompose.ui.square.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.api.apiCall
import top.wangchenyan.wancompose.ui.home.model.Article
import top.wangchenyan.wancompose.ui.mine.viewmodel.CollectViewModel
import top.wangchenyan.wancompose.widget.LoadState
import top.wangchenyan.wancompose.widget.Toaster

/**
 * Created by wcy on 2021/4/1.
 */
class SquareViewModel : ViewModel() {
    var pageState by mutableStateOf(LoadState.LOADING)
    var showLoading by mutableStateOf(false)
    var list by mutableStateOf(listOf<Article>())
    var refreshingState by mutableStateOf(false)
    var loadState by mutableStateOf(false)
    private var page = 0

    init {
        firstLoad()
    }

    fun firstLoad() {
        viewModelScope.launch {
            page = 0
            pageState = LoadState.LOADING
            val articleList = apiCall { Api.get().getSquareArticleList() }
            if (articleList.isSuccess()) {
                pageState = LoadState.SUCCESS
                list = articleList.data!!.datas
            } else {
                pageState = LoadState.FAIL
            }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            page = 0
            refreshingState = true
            val articleList = apiCall { Api.get().getSquareArticleList() }
            if (articleList.isSuccess()) {
                list = articleList.data!!.datas
                refreshingState = false
            } else {
                refreshingState = false
                Toaster.show("加载失败")
            }
        }
    }

    fun onLoad() {
        viewModelScope.launch {
            loadState = true
            val articleList = apiCall { Api.get().getSquareArticleList(page + 1) }
            if (articleList.isSuccess()) {
                page++
                list = list.toMutableList().apply {
                    addAll(articleList.data!!.datas)
                }
                loadState = false
            } else {
                loadState = false
                Toaster.show("加载失败")
            }
        }
    }

    fun collect(article: Article) {
        viewModelScope.launch {
            showLoading = true
            CollectViewModel.collect(article)
            showLoading = false
        }
    }
}