package top.wangchenyan.wancompose.ui.search.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.king.ultraswiperefresh.UltraSwipeRefreshState
import kotlinx.coroutines.launch
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.common.utils.ToastUtils
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.ui.home.model.Article
import top.wangchenyan.wancompose.ui.mine.viewmodel.CollectViewModel
import top.wangchenyan.wancompose.widget.LoadState

class SearchResultViewModel : ViewModel() {
    var pageState by mutableStateOf(LoadState.LOADING)
    var showLoading by mutableStateOf(false)
    var list by mutableStateOf(listOf<Article>())
    var refreshState by mutableStateOf(
        UltraSwipeRefreshState(
            isRefreshing = false,
            isLoading = false
        )
    )
    private var keyword = ""
    private var page = 0

    fun setKeyword(keyword: String) {
        if (this.keyword.isEmpty()) {
            this.keyword = keyword
            firstLoad()
        }
    }

    fun firstLoad() {
        viewModelScope.launch {
            page = 0
            pageState = LoadState.LOADING
            val articleList = apiCall { Api.get().search(page, keyword) }
            if (articleList.isSuccessWithData()) {
                pageState = LoadState.SUCCESS
                if (articleList.getDataOrThrow().datas.isNotEmpty()) {
                    list = articleList.getDataOrThrow().datas.onEach { it.setSpannableTitle() }
                } else {
                    pageState = LoadState.EMPTY
                }
            } else {
                pageState = LoadState.FAIL
            }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            page = 0
            refreshState.isRefreshing = true
            val articleList = apiCall { Api.get().search(page, keyword) }
            if (articleList.isSuccessWithData()) {
                list = articleList.getDataOrThrow().datas.onEach { it.setSpannableTitle() }
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
            val articleList = apiCall { Api.get().search(page + 1, keyword) }
            if (articleList.isSuccessWithData()) {
                page++
                list = list.toMutableList().apply {
                    addAll(articleList.getDataOrThrow().datas.onEach { it.setSpannableTitle() })
                }
                refreshState.isLoading = false
            } else {
                refreshState.isLoading = false
                ToastUtils.show("加载失败")
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

    private fun Article.setSpannableTitle() {
        val htmlTitle = this.title.replace(Regex("<em\\s.+?>(\\w+)</em>")) { matchResult ->
            val title = matchResult.groupValues.getOrElse(1) {
                matchResult.groupValues.firstOrNull() ?: ""
            }
            "<strong>${title}</strong>"
        }
        this.setSpannableTitle(
            HtmlCompat.fromHtml(
                htmlTitle,
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        )
    }
}