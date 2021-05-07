package me.wcy.wanandroid.compose.ui.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.wcy.wanandroid.compose.api.Api
import me.wcy.wanandroid.compose.api.apiCall
import me.wcy.wanandroid.compose.ui.home.model.ArticleTag
import me.wcy.wanandroid.compose.widget.LoadState

/**
 * Created by wcy on 2021/4/1.
 */
class HomeViewModel : ViewModel() {
    var state by mutableStateOf(LoadState.LOADING)
    var list by mutableStateOf(mutableListOf<Any>())

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            val bannerDeffer = async { apiCall { Api.get().getHomeBanner() } }
            val stickDeffer = async { apiCall { Api.get().getStickyArticle() } }
            val articleDeffer = async { apiCall { Api.get().getHomeArticleList() } }
            val bannerRes = bannerDeffer.await()
            val stickyRes = stickDeffer.await()
            val articleRes = articleDeffer.await()
            if (bannerRes.isSuccess() && articleRes.isSuccess() && stickyRes.isSuccess()) {
                state = LoadState.SUCCESS
                list = list.apply {
                    clear()
                    add(bannerRes.data!!)
                    addAll(stickyRes.data!!.onEach {
                        it.tags.add(0, ArticleTag("置顶"))
                    })
                    addAll(articleRes.data!!.datas)
                }
            } else {
                state = LoadState.FAIL
            }
        }
    }
}