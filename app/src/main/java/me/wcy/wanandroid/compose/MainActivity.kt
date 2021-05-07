package me.wcy.wanandroid.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.wcy.wanandroid.compose.ui.theme.WanandroidTheme

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