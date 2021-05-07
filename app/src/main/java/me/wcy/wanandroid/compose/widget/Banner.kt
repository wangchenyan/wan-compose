package me.wcy.wanandroid.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.wcy.wanandroid.compose.theme.Colors

/**
 * Created by wcy on 2021/4/1.
 */

data class BannerData(
    val title: String,
    val imageUrl: String,
    val jumpUrl: String
)

class BannerViewModel : ViewModel() {
    var pagerState by mutableStateOf(PagerState())
    private var count = 0

    init {
        viewModelScope.launch {
            repeat(Int.MAX_VALUE) {
                delay(3000)
                if (count > 0) {
                    pagerState.currentPage = (pagerState.currentPage + 1) % count
                }
            }
        }
    }

    fun setCount(count: Int) {
        this.count = count
        pagerState.maxPage = count - 1
    }
}

@Composable
fun Banner(
    modifier: Modifier,
    dataList: List<BannerData>
) {
    val viewModel: BannerViewModel = viewModel()
    viewModel.setCount(dataList.size)
    Pager(state = viewModel.pagerState, modifier = modifier, offscreenLimit = dataList.size - 1) {
        val bannerData = dataList[page]
        Box(modifier = Modifier.fillMaxSize()) {
            GlideImage(
                bannerData.imageUrl,
                Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color(0xFF60000000))
                    .padding(16.dp, 4.dp)
            ) {
                Text(
                    text = bannerData.title,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    color = Colors.white,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    modifier = Modifier
                        .width(2.dp)
                        .height(0.dp)
                )
                for (i in dataList.indices) {
                    Spacer(
                        modifier = Modifier
                            .width(8.dp)
                            .height(0.dp)
                    )
                    val color =
                        if (i == viewModel.pagerState.currentPage) Colors.white else Color.LightGray
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(5.dp)
                            .height(5.dp)
                            .clip(CircleShape)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                        )
                    }
                }
            }
        }
    }
}
