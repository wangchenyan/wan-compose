package me.wcy.wanandroid.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import me.wcy.wanandroid.compose.ui.home.Home
import me.wcy.wanandroid.compose.ui.mine.Mine
import me.wcy.wanandroid.compose.ui.square.Square
import me.wcy.wanandroid.compose.ui.wechat.WeChat
import me.wcy.wanandroid.compose.widget.BottomTab

@ExperimentalPagerApi
@Composable
fun Main(navController: NavHostController) {
    Column(Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(pageCount = 4, initialOffscreenLimit = 3)
        HorizontalPager(pagerState, Modifier.weight(1f)) {
            when (currentPage) {
                0 -> {
                    Home(navController)
                }
                1 -> {
                    Square(navController)
                }
                2 -> {
                    WeChat(navController)
                }
                3 -> {
                    Mine(navController)
                }
            }
        }
        BottomTab(pagerState.currentPage) {
            scope.launch {
                pagerState.scrollToPage(it)
            }
        }
    }
}