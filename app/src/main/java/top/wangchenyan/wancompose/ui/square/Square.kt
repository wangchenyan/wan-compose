package top.wangchenyan.wancompose.ui.square

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.king.ultraswiperefresh.NestedScrollMode
import com.king.ultraswiperefresh.UltraSwipeRefresh
import top.wangchenyan.wancompose.R
import top.wangchenyan.wancompose.theme.Colors
import top.wangchenyan.wancompose.ui.home.ArticleItem
import top.wangchenyan.wancompose.ui.square.viewmodel.SquareViewModel
import top.wangchenyan.wancompose.widget.PageLoading
import top.wangchenyan.wancompose.widget.TitleLayout
import top.wangchenyan.wancompose.widget.Toaster

/**
 * Created by wcy on 2021/3/31.
 */

@Composable
fun Square(navController: NavHostController) {
    val viewModel: SquareViewModel = viewModel()
    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        TitleLayout(
            title = "广场",
            menuIcon = R.drawable.ic_share,
            onMenuClick = {
                Toaster.show("分享")
            }
        )
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
                        .background(Colors.white)
                ) {
                    itemsIndexed(viewModel.list) { index, item ->
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