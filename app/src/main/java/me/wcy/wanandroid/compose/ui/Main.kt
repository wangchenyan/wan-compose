package me.wcy.wanandroid.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import me.wcy.wanandroid.compose.ui.widget.BottomTab
import me.wcy.wanandroid.compose.ui.widget.Pager
import me.wcy.wanandroid.compose.viewmodel.MainViewModel

@Composable
fun Main(navController: NavHostController) {
    Column(Modifier.fillMaxSize()) {
        val mainViewModel: MainViewModel = viewModel()
        Pager(mainViewModel.pagerState, Modifier.weight(1f)) {
            when (page) {
                0 -> {
                    Home(navController)
                }
                1 -> {
                    Square()
                }
                2 -> {
                    WeChat()
                }
                3 -> {
                    Mine()
                }
            }
        }
        BottomTab(mainViewModel.pagerState.currentPage) {
            mainViewModel.pagerState.currentPage = it
        }
    }
}