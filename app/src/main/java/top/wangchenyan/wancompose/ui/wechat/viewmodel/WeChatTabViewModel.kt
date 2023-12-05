package top.wangchenyan.wancompose.ui.wechat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.king.ultraswiperefresh.UltraSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import top.wangchenyan.android.common.net.apiCall
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.ui.home.model.Article
import top.wangchenyan.wancompose.ui.mine.viewmodel.CollectViewModel
import top.wangchenyan.wancompose.widget.LoadState
import top.wangchenyan.wancompose.widget.Toaster

class WeChatTabViewModel(private val scope: CoroutineScope, private val id: Long) {
    var pageState by mutableStateOf(LoadState.LOADING)
    var showLoading by mutableStateOf(false)
    var articleList by mutableStateOf(listOf<Article>())
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
        scope.launch {
            page = 0
            pageState = LoadState.LOADING
            val res = apiCall { Api.get().getWeChatArticleList(id) }
            if (res.isSuccessWithData()) {
                pageState = LoadState.SUCCESS
                articleList = res.data!!.datas
            } else {
                pageState = LoadState.FAIL
            }
        }
    }

    fun loadArticleList() {
        scope.launch {
            refreshState.isLoading = true
            val res = apiCall { Api.get().getWeChatArticleList(id, page + 1) }
            if (res.isSuccessWithData()) {
                page++
                articleList = articleList.toMutableList().apply {
                    addAll(res.data!!.datas)
                }
                refreshState.isLoading = false
            } else {
                refreshState.isLoading = false
                Toaster.show("加载失败")
            }
        }
    }

    fun collect(article: Article) {
        scope.launch {
            showLoading = true
            CollectViewModel.collect(article)
            showLoading = false
        }
    }
}