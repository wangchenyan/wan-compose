package top.wangchenyan.wancompose.ui.mine.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.auth.AuthManager
import top.wangchenyan.wancompose.auth.User

class MineViewModel : ViewModel() {
    var user by mutableStateOf(AuthManager.user.value)
    var showLoading by mutableStateOf(false)
    var showDialog by mutableStateOf(false)
    private val userObserver = Observer<User?> {
        user = it?.copy()
    }

    init {
        AuthManager.user.observeForever(userObserver)
    }

    fun logout() {
        viewModelScope.launch {
            showLoading = true
            apiCall { Api.get().logout() }
            showLoading = false
            AuthManager.onLogout()
        }
    }

    override fun onCleared() {
        AuthManager.user.removeObserver(userObserver)
        super.onCleared()
    }
}
