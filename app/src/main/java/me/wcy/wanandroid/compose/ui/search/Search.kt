package me.wcy.wanandroid.compose.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.navigation.compose.navigate
import me.wcy.wanandroid.compose.R
import me.wcy.wanandroid.compose.theme.Colors
import me.wcy.wanandroid.compose.ui.search.viewmodel.SearchViewModel
import me.wcy.wanandroid.compose.widget.FlowLayout
import me.wcy.wanandroid.compose.widget.Toaster

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
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "热门搜索")
            Spacer(modifier = Modifier.height(16.dp))
            FlowLayout(
                verticalSpacing = 8.dp,
                horizontalSpacing = 8.dp,
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
                                navController.navigate("search_result?keyword=${viewModel.keyword}")
                            }
                    ) {
                        Text(
                            text = it.name,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 14.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
