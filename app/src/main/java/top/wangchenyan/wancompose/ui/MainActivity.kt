package top.wangchenyan.wancompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.accompanist.pager.ExperimentalPagerApi
import top.wangchenyan.wancompose.ComposeNavigation
import top.wangchenyan.wancompose.theme.WanandroidTheme

class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanandroidTheme {
                ComposeNavigation()
            }
        }
    }
}