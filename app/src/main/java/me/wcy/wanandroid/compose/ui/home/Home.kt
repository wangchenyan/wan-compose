package me.wcy.wanandroid.compose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import me.wcy.wanandroid.compose.R
import me.wcy.wanandroid.compose.theme.Colors
import me.wcy.wanandroid.compose.ui.home.model.Article
import me.wcy.wanandroid.compose.ui.home.model.HomeBannerData
import me.wcy.wanandroid.compose.ui.home.viewmodel.HomeViewModel
import me.wcy.wanandroid.compose.widget.*

/**
 * Created by wcy on 2021/3/31.
 */

@Composable
fun Home(navController: NavHostController) {
    val viewModel: HomeViewModel = viewModel()
    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        TitleBar(
            title = "首页",
            icon = R.drawable.ic_search,
            onIconClick = {
                Toaster.show("搜索")
            }
        )
        PageLoading(loadState = viewModel.pageState, onReload = { viewModel.firstLoad() }) {
            SwipeToRefreshAndLoadLayout(
                refreshingState = viewModel.refreshingState,
                loadState = viewModel.loadState,
                onRefresh = { viewModel.onRefresh() },
                onLoad = { viewModel.onLoad() }) {
                LazyColumn(Modifier.fillMaxSize()) {
                    itemsIndexed(viewModel.list) { index, item ->
                        if (item is List<*>) {
                            BannerItem(navController, item as List<HomeBannerData>)
                        } else if (item is Article) {
                            ArticleItem(navController, item)
                            Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BannerItem(navController: NavHostController, list: List<HomeBannerData>) {
    val dataList = list.map {
        BannerData(it.title, it.imagePath, it.url)
    }
    Banner(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        dataList = dataList
    )
}

@Composable
fun ArticleItem(navController: NavHostController, article: Article) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Colors.white)
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
            Text(
                article.superChapterName + " / " + article.chapterName,
                Modifier.padding(top = 12.dp),
                Colors.text_h2,
                12.sp,
            )
        }
    }
}