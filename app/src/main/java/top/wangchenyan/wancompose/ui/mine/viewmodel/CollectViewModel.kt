package top.wangchenyan.wancompose.ui.mine.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.king.ultraswiperefresh.UltraSwipeRefreshState
import kotlinx.coroutines.launch
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.common.utils.ToastUtils
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.ui.home.model.Article
import top.wangchenyan.wancompose.widget.LoadState

/**
 * Created by wcy on 2021/4/1.
 */
class CollectViewModel : ViewModel() {
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
            val articleList = apiCall { Api.get().getCollectArticleList() }
            if (articleList.isSuccessWithData()) {
                pageState = LoadState.SUCCESS
                list = articleList.data!!.datas.onEach { it.collect = true }
            } else {
                pageState = LoadState.FAIL
            }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            page = 0
            refreshState.isRefreshing = true
            val articleList = apiCall { Api.get().getCollectArticleList() }
            if (articleList.isSuccessWithData()) {
                list = articleList.data!!.datas.onEach { it.collect = true }
                refreshState.isRefreshing = false
            } else {
                refreshState.isRefreshing = false
                ToastUtils.show("加载失败")
            }
        }
    }

    fun onLoad() {
        viewModelScope.launch {
            refreshState.isLoading = true
            val articleList = apiCall { Api.get().getCollectArticleList(page + 1) }
            if (articleList.isSuccessWithData()) {
                page++
                list = list.toMutableList().apply {
                    addAll(articleList.data!!.datas.onEach { it.collect = true })
                }
                refreshState.isLoading = false
            } else {
                refreshState.isLoading = false
                ToastUtils.show("加载失败")
            }
        }
    }

    fun uncollect(article: Article) {
        viewModelScope.launch {
            showLoading = true
            val res = collect(article) { it.originId }
            if (res) {
                onRefresh()
            }
            showLoading = false
        }
    }

    companion object {
        suspend fun collect(
            article: Article,
            getId: (article: Article) -> Long = { it.id }
        ): Boolean {
            val id = getId.invoke(article)
            if (article.collect) {
                val res = apiCall { Api.get().uncollect(id) }
                if (res.isSuccess()) {
                    article.collect = false
                    return true
                } else {
                    ToastUtils.show(res.msg)
                }
            } else {
                val res = apiCall { Api.get().collect(id) }
                if (res.isSuccess()) {
                    article.collect = true
                    return true
                } else {
                    ToastUtils.show(res.msg)
                }
            }
            return false
        }
    }
}