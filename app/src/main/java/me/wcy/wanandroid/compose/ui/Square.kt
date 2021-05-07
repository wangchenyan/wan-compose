package me.wcy.wanandroid.compose.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Created by wcy on 2021/3/31.
 */

@Composable
fun Square() {
    Text(text = "广场", Modifier.fillMaxSize())
}