package top.wangchenyan.wancompose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.king.ultraswiperefresh.NestedScrollMode
import com.king.ultraswiperefresh.UltraSwipeRefresh
import top.wangchenyan.wancompose.R
import top.wangchenyan.wancompose.theme.AppTheme
import top.wangchenyan.wancompose.ui.home.model.Article
import top.wangchenyan.wancompose.ui.home.model.HomeBannerData
import top.wangchenyan.wancompose.ui.home.viewmodel.HomeViewModel
import top.wangchenyan.wancompose.widget.ArticleItem
import top.wangchenyan.wancompose.widget.Banner
import top.wangchenyan.wancompose.widget.BannerData
import top.wangchenyan.wancompose.widget.PageLoading
import top.wangchenyan.wancompose.widget.TitleLayout

/**
 * Created by wcy on 2021/3/31.
 */

@ExperimentalPagerApi
@Composable
fun Home(navController: NavHostController) {
    val viewModel: HomeViewModel = viewModel()
    val colors = AppTheme.colors
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.bg)
    ) {
        TitleLayout(
            title = "首页",
            menuIcon = R.drawable.ic_search,
            onMenuClick = {
                navController.navigate("search")
            }
        )
        HomeContent(navController, viewModel)
    }
}

@ExperimentalPagerApi
@Composable
private fun HomeContent(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    val colors = AppTheme.colors
    PageLoading(
        loadState = viewModel.pageState,
        showLoading = viewModel.showLoading,
        onReload = { viewModel.firstLoad() }
    ) {
        UltraSwipeRefresh(
            state = viewModel.refreshState,
            onRefresh = { viewModel.onRefresh() },
            onLoadMore = { viewModel.onLoad() },
            headerScrollMode = NestedScrollMode.FixedContent,
            footerScrollMode = NestedScrollMode.FixedContent,
        ) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(colors.bgOverlay)
            ) {
                itemsIndexed(
                    items = viewModel.list,
                    key = { index, item ->
                        when (item) {
                            is List<*> -> "banner"
                            is Article -> item.id
                            else -> index
                        }
                    },
                    contentType = { _, item ->
                        when (item) {
                            is List<*> -> "banner"
                            is Article -> "article"
                            else -> "unknown"
                        }
                    }
                ) { _, item ->
                    if (item is List<*>) {
                        BannerItem(navController, item as List<HomeBannerData>)
                    } else if (item is Article) {
                        ArticleItem(navController, item) {
                            viewModel.collect(item)
                        }
                        HorizontalDivider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun BannerItem(navController: NavHostController, list: List<HomeBannerData>) {
    val dataList = remember(list) {
        list.map {
            BannerData(it.title, it.imagePath, it.url)
        }
    }
    Banner(
        navController = navController,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        dataList = dataList
    )
}
