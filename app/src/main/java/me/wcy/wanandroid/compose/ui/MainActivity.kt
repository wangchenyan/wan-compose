package me.wcy.wanandroid.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.wcy.wanandroid.compose.ComposeNavigation
import me.wcy.wanandroid.compose.theme.WanandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanandroidTheme {
                ComposeNavigation()
            }
        }
    }
}