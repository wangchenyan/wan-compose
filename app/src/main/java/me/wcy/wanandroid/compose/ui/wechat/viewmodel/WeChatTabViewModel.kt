package me.wcy.wanandroid.compose.ui.wechat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.wcy.wanandroid.compose.api.Api
import me.wcy.wanandroid.compose.api.apiCall
import me.wcy.wanandroid.compose.ui.home.model.Article
import me.wcy.wanandroid.compose.ui.mine.viewmodel.CollectViewModel
import me.wcy.wanandroid.compose.widget.Toaster

class WeChatTabViewModel(private val scope: CoroutineScope, private val id: Long) {
    var showLoading by mutableStateOf(false)
    var articleList by mutableStateOf(listOf<Article>())
    var refreshState by mutableStateOf(false)
    var loadState by mutableStateOf(false)
    private var pageMap = 0

    init {
        scope.launch {
            refreshState = true
            pageMap = 0
            val res = apiCall { Api.get().getWeChatArticleList(id) }
            if (res.isSuccess()) {
                articleList = res.data!!.datas
                refreshState = false
            } else {
                refreshState = false
                Toaster.show("加载失败")
            }
        }
    }

    fun loadArticleList() {
        scope.launch {
            loadState = true
            val res = apiCall { Api.get().getWeChatArticleList(id, pageMap + 1) }
            if (res.isSuccess()) {
                pageMap++
                articleList = articleList.toMutableList().apply {
                    addAll(res.data!!.datas)
                }
                loadState = false
            } else {
                loadState = false
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