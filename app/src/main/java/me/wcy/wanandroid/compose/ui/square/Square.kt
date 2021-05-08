package me.wcy.wanandroid.compose.ui.square

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import me.wcy.wanandroid.compose.ui.home.ArticleItem
import me.wcy.wanandroid.compose.ui.square.viewmodel.SquareViewModel
import me.wcy.wanandroid.compose.widget.PageLoading
import me.wcy.wanandroid.compose.widget.SwipeToRefreshAndLoadLayout
import me.wcy.wanandroid.compose.widget.TitleBar

/**
 * Created by wcy on 2021/3/31.
 */

@Composable
fun Square(navController: NavHostController) {
    val viewModel: SquareViewModel = viewModel()
    Column(Modifier.fillMaxSize()) {
        TitleBar(title = "广场")
        PageLoading(
            loadState = viewModel.pageState,
            onReload = { viewModel.firstLoad() }) {
            SwipeToRefreshAndLoadLayout(
                refreshingState = viewModel.refreshingState,
                loadState = viewModel.loadState,
                onRefresh = { viewModel.onRefresh() },
                onLoad = { viewModel.onLoad() }) {
                LazyColumn(Modifier.fillMaxSize()) {
                    itemsIndexed(viewModel.list) { index, item ->
                        ArticleItem(navController, item)
                        Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}