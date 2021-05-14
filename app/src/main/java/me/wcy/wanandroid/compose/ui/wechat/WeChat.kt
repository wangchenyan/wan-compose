package me.wcy.wanandroid.compose.ui.wechat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import me.wcy.wanandroid.compose.theme.Colors
import me.wcy.wanandroid.compose.ui.home.ArticleItem
import me.wcy.wanandroid.compose.ui.wechat.model.WeChatTabViewModel
import me.wcy.wanandroid.compose.ui.wechat.viewmodel.WeChatViewModel
import me.wcy.wanandroid.compose.widget.PageLoading
import me.wcy.wanandroid.compose.widget.Pager
import me.wcy.wanandroid.compose.widget.SwipeToLoadLayout
import me.wcy.wanandroid.compose.widget.TitleBar

/**
 * Created by wcy on 2021/3/31.
 */

@Composable
fun WeChat(navController: NavHostController) {
    val viewModel: WeChatViewModel = viewModel()
    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        TitleBar(title = "公众号")
        PageLoading(
            loadState = viewModel.pageState,
            onReload = { viewModel.getAuthorList() }) {
            if (viewModel.authorList.isNotEmpty()) {
                Column(Modifier.fillMaxSize()) {
                    ScrollableTabRow(
                        selectedTabIndex = viewModel.pagerState.currentPage,
                        modifier = Modifier
                            .fillMaxWidth(),
                        backgroundColor = Color.Transparent,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[viewModel.pagerState.currentPage])
                                    .padding(start = 20.dp, end = 20.dp),
                                color = Colors.main
                            )
                        },
                        divider = {}
                    ) {
                        viewModel.authorList.forEachIndexed { index, weChatAuthor ->
                            Tab(
                                modifier = Modifier.padding(vertical = 10.dp),
                                selected = (index == viewModel.pagerState.currentPage),
                                onClick = {
                                    viewModel.pagerState.currentPage = index
                                }) {
                                Text(text = weChatAuthor.name)
                            }
                        }
                    }
                    Divider()
                    Pager(
                        state = viewModel.pagerState,
                        modifier = Modifier.fillMaxSize(),
                        offscreenLimit = viewModel.authorList.size - 1
                    ) {
                        WeChatTab(
                            navController,
                            viewModel.viewModelScope,
                            viewModel.authorList[page].id
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeChatTab(navController: NavHostController, scope: CoroutineScope, id: Long) {
    val viewModel = remember {
        WeChatTabViewModel(scope, id)
    }
    Column(Modifier.fillMaxSize()) {
        PageLoading(showLoading = viewModel.showLoading) {
            SwipeToLoadLayout(
                loadState = viewModel.loadState,
                onLoad = { viewModel.loadArticleList() }) {
                LazyColumn(Modifier.fillMaxSize()) {
                    itemsIndexed(viewModel.articleList) { index, item ->
                        ArticleItem(navController, item) {
                            viewModel.collect(item)
                        }
                        Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}