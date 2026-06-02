package top.wangchenyan.wancompose.ui.square

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.king.ultraswiperefresh.NestedScrollMode
import com.king.ultraswiperefresh.UltraSwipeRefresh
import top.wangchenyan.common.ext.toast
import top.wangchenyan.wancompose.R
import top.wangchenyan.wancompose.theme.AppTheme
import top.wangchenyan.wancompose.ui.home.ArticleItem
import top.wangchenyan.wancompose.ui.square.viewmodel.SquareViewModel
import top.wangchenyan.wancompose.widget.PageLoading
import top.wangchenyan.wancompose.widget.TitleLayout

/**
 * Created by wcy on 2021/3/31.
 */

@Composable
fun Square(navController: NavHostController) {
    val viewModel: SquareViewModel = viewModel()
    val colors = AppTheme.colors
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.bg)
    ) {
        TitleLayout(
            title = "广场",
            menuIcon = R.drawable.ic_share,
            onMenuClick = {
                toast("分享")
            }
        )
        SquareContent(navController, viewModel)
    }
}

@Composable
private fun SquareContent(
    navController: NavHostController,
    viewModel: SquareViewModel
) {
    val colors = AppTheme.colors
    PageLoading(
        loadState = viewModel.pageState,
        onReload = { viewModel.firstLoad() },
        showLoading = viewModel.showLoading
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
                    key = { _, item -> item.id }
                ) { _, item ->
                    ArticleItem(navController, item) {
                        viewModel.collect(item)
                    }
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                }
            }
        }
    }
}
