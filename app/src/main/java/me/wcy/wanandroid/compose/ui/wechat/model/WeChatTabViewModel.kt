package me.wcy.wanandroid.compose.ui.wechat.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.wcy.wanandroid.compose.api.Api
import me.wcy.wanandroid.compose.api.apiCall
import me.wcy.wanandroid.compose.ui.home.model.Article
import me.wcy.wanandroid.compose.widget.Toaster

class WeChatTabViewModel(private val scope: CoroutineScope, private val id: Long) {
    val articleListMap by mutableStateOf(mutableListOf<Article>())
    var refreshStateMap by mutableStateOf(false)
    var loadStateMap by mutableStateOf(false)
    private var pageMap = 0

    init {
        scope.launch {
            refreshStateMap = true
            pageMap = 0
            val articleList = apiCall { Api.get().getWeChatArticleList(id) }
            if (articleList.isSuccess()) {
                articleListMap.clear()
                articleListMap.addAll(articleList.data!!.datas)
                refreshStateMap = false
            } else {
                refreshStateMap = false
                Toaster.show("加载失败")
            }
        }
    }

    fun loadArticleList() {
        scope.launch {
            loadStateMap = true
            val articleList = apiCall { Api.get().getWeChatArticleList(id, pageMap + 1) }
            if (articleList.isSuccess()) {
                pageMap++
                articleListMap.addAll(articleList.data!!.datas)
                loadStateMap = false
            } else {
                loadStateMap = false
                Toaster.show("加载失败")
            }
        }
    }
}