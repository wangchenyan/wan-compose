package top.wangchenyan.wancompose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.king.ultraswiperefresh.NestedScrollMode
import com.king.ultraswiperefresh.UltraSwipeRefresh
import top.wangchenyan.wancompose.R
import top.wangchenyan.wancompose.auth.AuthManager
import top.wangchenyan.wancompose.theme.Colors
import top.wangchenyan.wancompose.ui.home.model.Article
import top.wangchenyan.wancompose.ui.home.model.HomeBannerData
import top.wangchenyan.wancompose.ui.home.viewmodel.HomeViewModel
import top.wangchenyan.wancompose.widget.Banner
import top.wangchenyan.wancompose.widget.BannerData
import top.wangchenyan.wancompose.widget.PageLoading
import top.wangchenyan.wancompose.widget.TitleLayout

/**
 * Created by wcy on 2021/3/31.
 */

@ExperimentalPagerApi
@Composable
fun Home(navController: NavHostController) {
    val viewModel: HomeViewModel = viewModel()
    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        TitleLayout(
            title = "首页",
            menuIcon = R.drawable.ic_search,
            onMenuClick = {
                navController.navigate("search")
            }
        )
        PageLoading(
            loadState = viewModel.pageState,
            showLoading = viewModel.showLoading,
            onReload = { viewModel.firstLoad() }) {
            UltraSwipeRefresh(
                state = viewModel.refreshState,
                onRefresh = { viewModel.onRefresh() },
                onLoadMore = { viewModel.onLoad() },
                headerScrollMode = NestedScrollMode.FixedContent,
                footerScrollMode = NestedScrollMode.FixedContent,
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .background(Colors.white)
                ) {
                    itemsIndexed(viewModel.list) { index, item ->
                        if (item is List<*>) {
                            BannerItem(navController, item as List<HomeBannerData>)
                        } else if (item is Article) {
                            ArticleItem(navController, item) {
                                viewModel.collect(item)
                            }
                            Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun BannerItem(navController: NavHostController, list: List<HomeBannerData>) {
    val dataList = list.map {
        BannerData(it.title, it.imagePath, it.url)
    }
    Banner(
        navController = navController,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        dataList = dataList
    )
}

@Composable
fun ArticleItem(
    navController: NavHostController,
    article: Article,
    onCollectClick: () -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navController.navigate("web?url=${article.link}")
        }) {
        Column(
            Modifier.padding(16.dp, 10.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                article.tags.forEach {
                    Text(
                        it.name,
                        Modifier
                            .align(Alignment.CenterVertically)
                            .border(0.5.dp, it.getColor(), RoundedCornerShape(3.dp))
                            .padding(2.dp, 1.dp),
                        it.getColor(),
                        10.sp
                    )
                    Spacer(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(8.dp)
                            .height(0.dp)
                    )
                }
                Text(
                    article.getAuthor(),
                    Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    Colors.text_h2,
                    12.sp
                )
                Spacer(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(10.dp)
                        .height(0.dp)
                )
                Text(
                    article.niceDate,
                    Modifier
                        .align(Alignment.CenterVertically),
                    Colors.text_h2,
                    12.sp
                )
            }
            Text(
                article.title,
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                Colors.text_h1,
                15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                val chapter = StringBuilder(article.superChapterName)
                if (article.superChapterName.isNotEmpty() && article.chapterName.isNotEmpty()) {
                    chapter.append(" / ")
                }
                chapter.append(article.chapterName)
                Text(
                    chapter.toString(),
                    Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    Colors.text_h2,
                    12.sp,
                )
                val iconRes = if (article.collect) R.drawable.ic_like_fill else R.drawable.ic_like
                val tint = if (article.collect) Colors.red else Colors.text_h2
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "收藏",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            if (!AuthManager.isLogin()) {
                                navController.navigate("login")
                            } else {
                                onCollectClick.invoke()
                            }
                        },
                    tint = tint
                )
            }
        }
    }
}