package me.wcy.wanandroid.compose.ui.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import me.wcy.wanandroid.compose.theme.Colors
import me.wcy.wanandroid.compose.ui.mine.viewmodel.LoginViewModel
import me.wcy.wanandroid.compose.widget.PageLoading
import me.wcy.wanandroid.compose.widget.TitleBar

@Composable
fun Login(navController: NavHostController) {
    val viewModel: LoginViewModel = viewModel()
    PageLoading(
        modifier = Modifier.background(Colors.background),
        showLoading = viewModel.showLoading
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            TitleBar(title = "登录", onBack = {
                navController.popBackStack()
            })
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .height(120.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "用户名",
                        modifier = Modifier
                            .width(60.dp)
                            .align(Alignment.CenterVertically)
                    )
                    BasicTextField(
                        value = viewModel.username,
                        onValueChange = { value ->
                            viewModel.username = value
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically),
                        singleLine = true,
                    )
                }
                Divider()
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "密码",
                        modifier = Modifier
                            .width(60.dp)
                            .align(Alignment.CenterVertically)
                    )
                    BasicTextField(
                        value = viewModel.password,
                        onValueChange = { value ->
                            viewModel.password = value
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true
                    )
                }
                Divider()
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = { viewModel.login(navController) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(22.dp),
                ) {
                    Text(text = "登录")
                }
            }
        }
    }
}