package me.wcy.wanandroid.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Created by wcy on 2021/4/1.
 */

enum class LoadState {
    LOADING,
    SUCCESS,
    FAIL
}

@Composable
fun PageLoading(
    modifier: Modifier = Modifier,
    loadState: LoadState = LoadState.SUCCESS,
    onReload: () -> Unit = {},
    showLoading: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (loadState) {
            LoadState.LOADING -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            LoadState.FAIL -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable { onReload.invoke() }) {
                    Text(
                        text = "加载失败，点击重试",
                        Modifier.align(Alignment.Center)
                    )
                }
            }
            LoadState.SUCCESS -> {
                content.invoke(this)
                if (showLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x80000000))
                            .clickable { }
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}