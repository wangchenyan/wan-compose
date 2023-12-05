package top.wangchenyan.wancompose.ui.wechat

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import top.wangchenyan.wancompose.theme.Colors
import top.wangchenyan.wancompose.ui.home.ArticleItem
import top.wangchenyan.wancompose.ui.wechat.viewmodel.WeChatTabViewModel
import top.wangchenyan.wancompose.ui.wechat.viewmodel.WeChatViewModel
import top.wangchenyan.wancompose.widget.PageLoading
import top.wangchenyan.wancompose.widget.SwipeLoadLayout
import top.wangchenyan.wancompose.widget.TitleBar

/**
 * Created by wcy on 2021/3/31.
 */

@ExperimentalPagerApi
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
                val scope = rememberCoroutineScope()
                val pagerState = rememberPagerState(
                    pageCount = viewModel.authorList.size,
                    initialOffscreenLimit = viewModel.authorList.size - 1
                )
                Column(Modifier.fillMaxSize()) {
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        modifier = Modifier
                            .fillMaxWidth(),
                        backgroundColor = Colors.titleBar,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                                    .padding(start = 20.dp, end = 20.dp),
                                color = Colors.main
                            )
                        },
                        divider = {}
                    ) {
                        viewModel.authorList.forEachIndexed { index, weChatAuthor ->
                            Tab(
                                modifier = Modifier.padding(vertical = 10.dp),
                                selected = (index == pagerState.currentPage),
                                onClick = {
                                    scope.launch {
                                        pagerState.scrollToPage(index)
                                    }
                                }) {
                                Text(text = weChatAuthor.name, fontSize = 16.sp)
                            }
                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        WeChatTab(
                            navController,
                            viewModel,
                            viewModel.authorList[currentPage].id
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeChatTab(navController: NavHostController, viewModel: WeChatViewModel, id: Long) {
    var tabViewModel = viewModel.tabViewModelMap[id]
    if (tabViewModel == null) {
        tabViewModel = WeChatTabViewModel(viewModel.viewModelScope, id)
        viewModel.tabViewModelMap.put(id, tabViewModel)
    }
    Column(Modifier.fillMaxSize()) {
        PageLoading(
            loadState = tabViewModel.pageState,
            onReload = { tabViewModel.firstLoad() },
            showLoading = tabViewModel.showLoading
        ) {
            SwipeLoadLayout(
                loadState = tabViewModel.loadState,
                onLoad = { tabViewModel.loadArticleList() }) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .background(Colors.white)
                ) {
                    itemsIndexed(tabViewModel.articleList) { index, item ->
                        ArticleItem(navController, item) {
                            tabViewModel.collect(item)
                        }
                        Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}