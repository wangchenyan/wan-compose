package me.wcy.wanandroid.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import me.wcy.wanandroid.compose.ui.home.Home
import me.wcy.wanandroid.compose.ui.mine.Mine
import me.wcy.wanandroid.compose.ui.square.Square
import me.wcy.wanandroid.compose.ui.wechat.WeChat
import me.wcy.wanandroid.compose.widget.BottomTab
import me.wcy.wanandroid.compose.widget.Pager
import me.wcy.wanandroid.compose.widget.PagerState

class MainViewModel : ViewModel() {
    var pagerState by mutableStateOf(PagerState(maxPage = 3))
}

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
                    Square(navController)
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