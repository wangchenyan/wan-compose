package top.wangchenyan.wancompose.ui.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import top.wangchenyan.wancompose.theme.Colors
import top.wangchenyan.wancompose.ui.mine.viewmodel.LoginViewModel
import top.wangchenyan.wancompose.widget.PageLoading
import top.wangchenyan.wancompose.widget.TitleBar

@Composable
fun Login(navController: NavHostController) {
    val viewModel: LoginViewModel = viewModel()
    PageLoading(
        modifier = Modifier.background(Colors.background),
        showLoading = viewModel.showLoading
    ) {
        Column(Modifier.fillMaxSize()) {
            TitleBar(title = "登录", onBack = {
                navController.popBackStack()
            })
            Column(Modifier.fillMaxSize()) {
                Spacer(
                    modifier = Modifier
                        .height(120.dp)
                )
                OutlinedTextField(
                    value = viewModel.username,
                    onValueChange = { value ->
                        viewModel.username = value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "请输入用户名")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { value ->
                        viewModel.password = value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "请输入密码")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = { viewModel.login(navController) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(percent = 50),
                ) {
                    Text(text = "登录", fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "没有账号？去注册",
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 16.dp)
                        .clickable {
                            navController.navigate("register")
                        },
                    color = Colors.text_h2,
                    fontSize = 15.sp
                )
            }
        }
    }
}