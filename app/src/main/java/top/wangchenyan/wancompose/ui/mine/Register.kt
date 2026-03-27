package top.wangchenyan.wancompose.ui.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import top.wangchenyan.wancompose.theme.Colors
import top.wangchenyan.wancompose.ui.mine.viewmodel.RegisterViewModel
import top.wangchenyan.wancompose.widget.PageLoading
import top.wangchenyan.wancompose.widget.TitleLayout

@Composable
fun Register(navController: NavHostController) {
    val viewModel: RegisterViewModel = viewModel()
    PageLoading(
        modifier = Modifier.background(Colors.background),
        showLoading = viewModel.showLoading
    ) {
        Column(Modifier.fillMaxSize()) {
            TitleLayout(title = "注册", onBack = {
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
                    colors = OutlinedTextFieldDefaults.colors(
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = viewModel.repassword,
                    onValueChange = { value ->
                        viewModel.repassword = value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "请确认密码")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = { viewModel.register(navController) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(percent = 50),
                ) {
                    Text(text = "注册", fontSize = 15.sp)
                }
            }
        }
    }
}