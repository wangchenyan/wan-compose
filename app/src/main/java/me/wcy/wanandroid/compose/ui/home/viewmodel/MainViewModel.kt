package me.wcy.wanandroid.compose.ui.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import me.wcy.wanandroid.compose.widget.PagerState

/**
 * Created by wcy on 2021/3/31.
 */

class MainViewModel : ViewModel() {
    var pagerState by mutableStateOf(PagerState(maxPage = 3))
}