package me.wcy.wanandroid.compose.ui.web.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WebViewModel : ViewModel() {
    var title by mutableStateOf("加载中...")
    var progress by mutableStateOf(0)
}