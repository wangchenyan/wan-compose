package top.wangchenyan.wancompose.ui.search

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import top.wangchenyan.wancompose.R
import top.wangchenyan.wancompose.theme.Colors
import top.wangchenyan.wancompose.ui.search.viewmodel.SearchViewModel
import top.wangchenyan.wancompose.widget.Toaster

@Composable
fun Search(navController: NavHostController) {
    val viewModel: SearchViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Colors.titleBar)
        ) {
            Icon(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        navController.popBackStack()
                    }
                    .size(48.dp)
                    .padding(14.dp),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "返回",
                tint = Colors.text_h1
            )
            BasicTextField(
                value = viewModel.keyword,
                onValueChange = {
                    viewModel.keyword = it
                },
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 16.dp, end = 16.dp)
                    .weight(1f),
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true
            )
            Icon(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        viewModel.keyword = ""
                    }
                    .size(48.dp)
                    .padding(14.dp),
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "清除",
                tint = Colors.text_h1
            )
            Icon(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        if (viewModel.keyword.isNotEmpty()) {
                            viewModel.addHistory(viewModel.keyword)
                            navController.navigate("search_result?keyword=${viewModel.keyword}")
                        } else {
                            Toaster.show("请输入关键字")
                        }
                    }
                    .size(48.dp)
                    .padding(14.dp),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "",
                tint = Colors.text_h1
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "热门搜索",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Colors.text_h1,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = 8.dp,
            ) {
                viewModel.hotKeys.forEach {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFDDDDDD),
                                shape = RoundedCornerShape(percent = 50)
                            )
                            .clickable {
                                viewModel.keyword = it.name
                                viewModel.addHistory(viewModel.keyword)
                                navController.navigate("search_result?keyword=${viewModel.keyword}")
                            }
                    ) {
                        Text(
                            text = it.name,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = Colors.text_h1,
                            fontSize = 14.sp,
                            maxLines = 1
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "搜索历史",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Colors.text_h1,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(viewModel.history) { index, item ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.keyword = item
                            navController.navigate("search_result?keyword=${viewModel.keyword}")
                        }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically),
                                color = Colors.text_h2,
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "删除",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        viewModel.removeHistory(item)
                                    },
                                tint = Colors.text_h2
                            )
                        }
                    }
                }
            }
        }
    }
}
