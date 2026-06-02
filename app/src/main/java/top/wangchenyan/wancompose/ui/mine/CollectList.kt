package top.wangchenyan.wancompose.ui.mine

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
import top.wangchenyan.wancompose.theme.AppTheme
import top.wangchenyan.wancompose.ui.mine.viewmodel.CollectViewModel
import top.wangchenyan.wancompose.widget.ArticleItem
import top.wangchenyan.wancompose.widget.PageLoading
import top.wangchenyan.wancompose.widget.TitleLayout

@Composable
fun CollectList(navController: NavHostController) {
    val viewModel: CollectViewModel = viewModel()
    val colors = AppTheme.colors
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.bg)
    ) {
        TitleLayout(title = "我的收藏", onBack = { navController.popBackStack() })
        CollectListContent(navController, viewModel)
    }
}

@Composable
private fun CollectListContent(
    navController: NavHostController,
    viewModel: CollectViewModel
) {
    val colors = AppTheme.colors
    PageLoading(
        loadState = viewModel.pageState,
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
                    key = { _, item -> item.id }
                ) { _, item ->
                    ArticleItem(navController, item) {
                        viewModel.uncollect(item)
                    }
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                }
            }
        }
    }
}
