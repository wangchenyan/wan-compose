package top.wangchenyan.wancompose.ui.mine.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.wancompose.api.Api

class RegisterViewModel : ViewModel() {
    var showLoading by mutableStateOf(false)
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var repassword by mutableStateOf("")

    fun register(navController: NavHostController) {
        viewModelScope.launch {
            if (username.isEmpty()) {
                toast("请输入用户名")
                return@launch
            }
            if (password.isEmpty()) {
                toast("请输入密码")
                return@launch
            }
            if (repassword.isEmpty()) {
                toast("请确认密码")
                return@launch
            }
            showLoading = true
            val registerRes = apiCall { Api.get().register(username, password, repassword) }
            showLoading = false
            if (registerRes.isSuccess()) {
                navController.popBackStack()
                toast("注册成功")
            } else {
                toast(registerRes.msg)
            }
        }
    }
}