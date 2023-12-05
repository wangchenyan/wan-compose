package top.wangchenyan.wancompose.ui.mine.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.api.apiCall
import top.wangchenyan.wancompose.widget.Toaster

class RegisterViewModel : ViewModel() {
    var showLoading by mutableStateOf(false)
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var repassword by mutableStateOf("")

    fun register(navController: NavHostController) {
        viewModelScope.launch {
            if (username.isEmpty()) {
                Toaster.show("请输入用户名")
                return@launch
            }
            if (password.isEmpty()) {
                Toaster.show("请输入密码")
                return@launch
            }
            if (repassword.isEmpty()) {
                Toaster.show("请确认密码")
                return@launch
            }
            showLoading = true
            val registerRes = apiCall { Api.get().register(username, password, repassword) }
            showLoading = false
            if (registerRes.isSuccessIgnoreData()) {
                navController.popBackStack()
                Toaster.show("注册成功")
            } else {
                Toaster.show(registerRes.msg)
            }
        }
    }
}