package top.wangchenyan.wancompose.widget

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import top.wangchenyan.wancompose.theme.Colors

/**
 * Created by wcy on 2021/4/1.
 */

data class BannerData(
    val title: String,
    val imageUrl: String,
    val jumpUrl: String
)

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalPagerApi
@Composable
fun Banner(
    navController: NavHostController,
    modifier: Modifier,
    dataList: List<BannerData>
) {
    val pagerState =
        rememberPagerState(pageCount = dataList.size, initialOffscreenLimit = dataList.size - 1)
    val handler = remember {
        Handler(Looper.getMainLooper())
    }
    val scope = rememberCoroutineScope()
    handler.removeCallbacksAndMessages(null)
    handler.postDelayed(object : Runnable {
        override fun run() {
            scope.launch {
                if (pagerState.pageCount > 0) {
                    pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
                }
            }
            handler.postDelayed(this, 3000)
        }
    }, 3000)
    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) {
        val bannerData = dataList[currentPage]
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                navController.navigate("web?url=${bannerData.jumpUrl}")
            }) {
            Image(
                painter = rememberCoilPainter(bannerData.imageUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
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
                        if (i == pagerState.currentPage) Colors.white else Color.LightGray
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
