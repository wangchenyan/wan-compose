package top.wangchenyan.wancompose.ui.square.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.king.ultraswiperefresh.UltraSwipeRefreshState
import kotlinx.coroutines.launch
import top.wangchenyan.android.common.net.apiCall
import top.wangchenyan.wancompose.api.Api
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
    var refreshState by mutableStateOf(
        UltraSwipeRefreshState(
            isRefreshing = false,
            isLoading = false
        )
    )
    private var page = 0

    init {
        firstLoad()
    }

    fun firstLoad() {
        viewModelScope.launch {
            page = 0
            pageState = LoadState.LOADING
            val articleList = apiCall { Api.get().getSquareArticleList() }
            if (articleList.isSuccessWithData()) {
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
            refreshState.isRefreshing = true
            val articleList = apiCall { Api.get().getSquareArticleList() }
            if (articleList.isSuccessWithData()) {
                list = articleList.data!!.datas
                refreshState.isRefreshing = false
            } else {
                refreshState.isRefreshing = false
                Toaster.show("加载失败")
            }
        }
    }

    fun onLoad() {
        viewModelScope.launch {
            refreshState.isLoading = true
            val articleList = apiCall { Api.get().getSquareArticleList(page + 1) }
            if (articleList.isSuccessWithData()) {
                page++
                list = list.toMutableList().apply {
                    addAll(articleList.data!!.datas)
                }
                refreshState.isLoading = false
            } else {
                refreshState.isLoading = false
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