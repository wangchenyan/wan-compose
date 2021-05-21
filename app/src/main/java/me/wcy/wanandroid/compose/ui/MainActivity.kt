package me.wcy.wanandroid.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.accompanist.pager.ExperimentalPagerApi
import me.wcy.wanandroid.compose.ComposeNavigation
import me.wcy.wanandroid.compose.theme.WanandroidTheme

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