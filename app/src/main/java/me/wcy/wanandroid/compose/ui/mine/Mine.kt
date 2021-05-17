package me.wcy.wanandroid.compose.ui.mine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import me.wcy.wanandroid.compose.ui.mine.viewmodel.MineViewModel
import me.wcy.wanandroid.compose.widget.PageLoading

/**
 * Created by wcy on 2021/3/31.
 */

@Composable
fun Mine(navController: NavHostController) {
    val viewModel: MineViewModel = viewModel()
    PageLoading(
        showLoading = viewModel.showLoading,
        modifier = Modifier.background(Colors.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.android_q),
                contentDescription = "",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clickable {
                        if (viewModel.user == null) {
                            navController.navigate("login")
                        }
                    }
            )
            Text(
                text = viewModel.user?.nickname ?: "未登录",
                modifier = Modifier.clickable {
                    if (viewModel.user == null) {
                        navController.navigate("login")
                    }
                },
                color = Colors.text_h1
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Box(
                    modifier = Modifier.background(Colors.green)
                ) {
                    Text(
                        text = "LV" + (viewModel.user?.level ?: ""),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        style = TextStyle(color = Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier.background(Colors.blue)
                ) {
                    Text(
                        text = "排名" + (viewModel.user?.rank ?: ""),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        style = TextStyle(color = Color.White)
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White)
                    .clickable {
                        if (viewModel.user == null) {
                            navController.navigate("login")
                        }
                    }
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "我的积分",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    color = Colors.text_h1
                )
                if (viewModel.user != null) {
                    Text(
                        text = viewModel.user!!.coinCount.toString(),
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = Colors.text_h2
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Divider(color = Colors.background, thickness = 0.5.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White)
                    .clickable {
                        if (viewModel.user == null) {
                            navController.navigate("login")
                        } else {
                            navController.navigate("collect")
                        }
                    }
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "我的收藏",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    color = Colors.text_h1
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically),
                    tint = Colors.text_h2
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Spacer(modifier = Modifier.height(50.dp))
            if (viewModel.user != null) {
                Button(
                    onClick = { viewModel.showDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(percent = 50),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Colors.red,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "退出登录", fontSize = 15.sp)
                }
            }
        }
        if (viewModel.showDialog) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.showDialog = false
                            viewModel.logout()
                        }
                    ) {
                        Text("确认")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            viewModel.showDialog = false
                        }
                    ) {
                        Text("取消")
                    }
                },
                title = {
                    Text(text = "提示")
                },
                text = {
                    Text(text = "确认退出登录？")
                }
            )
        }
    }
}