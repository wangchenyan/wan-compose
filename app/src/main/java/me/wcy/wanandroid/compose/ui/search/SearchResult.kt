package me.wcy.wanandroid.compose.ui.search

import androidx.compose.foundation.background
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
import me.wcy.wanandroid.compose.theme.Colors
import me.wcy.wanandroid.compose.ui.home.ArticleItem
import me.wcy.wanandroid.compose.ui.search.viewmodel.SearchResultViewModel
import me.wcy.wanandroid.compose.widget.PageLoading
import me.wcy.wanandroid.compose.widget.SwipeToRefreshAndLoadLayout
import me.wcy.wanandroid.compose.widget.TitleBar

@Composable
fun SearchResult(navController: NavHostController, keyword: String) {
    val viewModel: SearchResultViewModel = viewModel()
    viewModel.setKeyword(keyword)
    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        TitleBar(title = keyword, onBack = {
            navController.popBackStack()
        })
        PageLoading(
            loadState = viewModel.pageState,
            onReload = { viewModel.firstLoad() },
            showLoading = viewModel.showLoading
        ) {
            SwipeToRefreshAndLoadLayout(
                refreshingState = viewModel.refreshingState,
                loadState = viewModel.loadState,
                onRefresh = { viewModel.onRefresh() },
                onLoad = { viewModel.onLoad() }) {
                LazyColumn(Modifier.fillMaxSize()) {
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