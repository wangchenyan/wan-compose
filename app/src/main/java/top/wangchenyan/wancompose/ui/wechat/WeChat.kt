package top.wangchenyan.wancompose.ui.wechat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.king.ultraswiperefresh.NestedScrollMode
import com.king.ultraswiperefresh.UltraSwipeRefresh
import kotlinx.coroutines.launch
import top.wangchenyan.wancompose.theme.AppTheme
import top.wangchenyan.wancompose.ui.home.ArticleItem
import top.wangchenyan.wancompose.ui.wechat.viewmodel.WeChatViewModel
import top.wangchenyan.wancompose.widget.PageLoading
import top.wangchenyan.wancompose.widget.TitleLayout

/**
 * Created by wcy on 2021/3/31.
 */

@ExperimentalPagerApi
@Composable
fun WeChat(navController: NavHostController) {
    val viewModel: WeChatViewModel = viewModel()
    val colors = AppTheme.colors
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.bg)
    ) {
        TitleLayout(title = "公众号")
        WeChatContent(navController, viewModel)
    }
}

@ExperimentalPagerApi
@Composable
private fun WeChatContent(
    navController: NavHostController,
    viewModel: WeChatViewModel
) {
    val colors = AppTheme.colors
    PageLoading(
        loadState = viewModel.pageState,
        onReload = { viewModel.getAuthorList() }
    ) {
        if (viewModel.authorList.isNotEmpty()) {
            val scope = rememberCoroutineScope()
            val pagerState = rememberPagerState(
                pageCount = viewModel.authorList.size,
                initialOffscreenLimit = 1
            )
            Column(Modifier.fillMaxSize()) {
                SecondaryScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier
                        .fillMaxWidth(),
                    containerColor = colors.titleBar,
                    indicator = {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier
                                .tabIndicatorOffset(pagerState.currentPage)
                                .padding(start = 20.dp, end = 20.dp),
                            color = colors.main
                        )
                    },
                    divider = {}
                ) {
                    viewModel.authorList.forEachIndexed { index, weChatAuthor ->
                        Tab(
                            modifier = Modifier.padding(vertical = 10.dp),
                            selected = index == pagerState.currentPage,
                            onClick = {
                                scope.launch {
                                    pagerState.scrollToPage(index)
                                }
                            }
                        ) {
                            Text(text = weChatAuthor.name, fontSize = 16.sp)
                        }
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    WeChatTab(
                        navController = navController,
                        viewModel = viewModel,
                        id = viewModel.authorList[currentPage].id
                    )
                }
            }
        }
    }
}

@Composable
fun WeChatTab(
    navController: NavHostController,
    viewModel: WeChatViewModel,
    id: Long
) {
    val tabViewModel = remember(id) {
        viewModel.getTabViewModel(id)
    }
    Column(Modifier.fillMaxSize()) {
        PageLoading(
            loadState = tabViewModel.pageState,
            onReload = { tabViewModel.firstLoad() },
            showLoading = tabViewModel.showLoading
        ) {
            UltraSwipeRefresh(
                state = tabViewModel.refreshState,
                refreshEnabled = false,
                loadMoreEnabled = true,
                onRefresh = {},
                onLoadMore = { tabViewModel.loadArticleList() },
                headerScrollMode = NestedScrollMode.FixedContent,
                footerScrollMode = NestedScrollMode.FixedContent,
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .background(AppTheme.colors.bgOverlay)
                ) {
                    itemsIndexed(
                        items = tabViewModel.articleList,
                        key = { _, item -> item.id }
                    ) { _, item ->
                        ArticleItem(navController, item) {
                            tabViewModel.collect(item)
                        }
                        HorizontalDivider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}
