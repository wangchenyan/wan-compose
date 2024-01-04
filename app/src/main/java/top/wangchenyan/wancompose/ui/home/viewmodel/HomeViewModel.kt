package top.wangchenyan.wancompose.ui.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.king.ultraswiperefresh.UltraSwipeRefreshState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.common.utils.ToastUtils
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.ui.home.model.Article
import top.wangchenyan.wancompose.ui.home.model.ArticleTag
import top.wangchenyan.wancompose.ui.mine.viewmodel.CollectViewModel
import top.wangchenyan.wancompose.widget.LoadState

/**
 * Created by wcy on 2021/4/1.
 */
class HomeViewModel : ViewModel() {
    var pageState by mutableStateOf(LoadState.LOADING)
    var showLoading by mutableStateOf(false)
    var list by mutableStateOf(listOf<Any>())
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
            val bannerDeffer = async { apiCall { Api.get().getHomeBanner() } }
            val stickDeffer = async { apiCall { Api.get().getStickyArticle() } }
            val articleDeffer = async { apiCall { Api.get().getHomeArticleList() } }
            val bannerRes = bannerDeffer.await()
            val stickyRes = stickDeffer.await()
            val articleRes = articleDeffer.await()
            if (bannerRes.isSuccessWithData() && articleRes.isSuccessWithData() && stickyRes.isSuccessWithData()) {
                pageState = LoadState.SUCCESS
                list = mutableListOf<Any>().apply {
                    add(bannerRes.data!!)
                    addAll(stickyRes.data!!.onEach {
                        it.tags.add(0, ArticleTag("置顶"))
                    })
                    addAll(articleRes.data!!.datas)
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
            val bannerDeffer = async { apiCall { Api.get().getHomeBanner() } }
            val stickDeffer = async { apiCall { Api.get().getStickyArticle() } }
            val articleDeffer = async { apiCall { Api.get().getHomeArticleList() } }
            val bannerRes = bannerDeffer.await()
            val stickyRes = stickDeffer.await()
            val articleRes = articleDeffer.await()
            if (bannerRes.isSuccessWithData() && articleRes.isSuccessWithData() && stickyRes.isSuccessWithData()) {
                list = mutableListOf<Any>().apply {
                    add(bannerRes.data!!)
                    addAll(stickyRes.data!!.onEach {
                        it.tags.add(0, ArticleTag("置顶"))
                    })
                    addAll(articleRes.data!!.datas)
                }
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
            val articleList = apiCall { Api.get().getHomeArticleList(page + 1) }
            if (articleList.isSuccessWithData()) {
                page++
                list = list.toMutableList().apply {
                    addAll(articleList.data!!.datas)
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
}