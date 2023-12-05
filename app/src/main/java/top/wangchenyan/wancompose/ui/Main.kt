package top.wangchenyan.wancompose.ui

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
import top.wangchenyan.wancompose.ui.home.Home
import top.wangchenyan.wancompose.ui.mine.Mine
import top.wangchenyan.wancompose.ui.square.Square
import top.wangchenyan.wancompose.ui.wechat.WeChat
import top.wangchenyan.wancompose.widget.BottomTab

@ExperimentalPagerApi
@Composable
fun Main(navController: NavHostController) {
    Column(Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(pageCount = 4, initialOffscreenLimit = 3)
        HorizontalPager(pagerState, Modifier.weight(1f)) { page ->
            when (page) {
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