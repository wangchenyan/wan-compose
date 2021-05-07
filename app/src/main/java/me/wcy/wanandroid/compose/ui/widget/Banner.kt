package me.wcy.wanandroid.compose.ui.widget

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.wcy.wanandroid.compose.ui.theme.Colors

/**
 * Created by wcy on 2021/4/1.
 */

data class BannerData(
    val title: String,
    val imageUrl: String,
    val jumpUrl: String
)

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Banner(
    modifier: Modifier,
    dataList: List<BannerData>
) {
    val pagerState: PagerState = remember {
        PagerState(maxPage = dataList.size - 1)
    }
    rememberCoroutineScope().launch {
        repeat(Int.MAX_VALUE) {
            delay(3000)
            pagerState.currentPage = (pagerState.currentPage + 1) % dataList.size
        }
    }
    Pager(state = pagerState, modifier) {
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
                    val color = if (i == pagerState.currentPage) Colors.white else Color.LightGray
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
