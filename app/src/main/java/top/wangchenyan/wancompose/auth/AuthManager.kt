package top.wangchenyan.wancompose.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.wangchenyan.android.common.CommonApp
import top.wangchenyan.android.common.net.apiCall
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.storage.AppPreferences

object AuthManager {
    private var userInternal = MutableLiveData<User?>()
    val user: LiveData<User?> = userInternal

    fun init() {
        CommonApp.appScope.launch {
            withContext(Dispatchers.Main) {
                userInternal.value = AppPreferences.getUser()
                if (isLogin()) {
                    updateUserCoin()
                }
            }
        }
    }

    private suspend fun updateUserCoin() {
        val userRes = apiCall { Api.get().getUserCoin() }
        if (userRes.isSuccessWithData()) {
            val user = userRes.data!!
            val rawUser = userInternal.value!!.apply {
                coinCount = user.coinCount
                level = user.level
                rank = user.rank
            }
            AppPreferences.setUser(rawUser)
            userInternal.postValue(rawUser)
        } else if (userRes.code == -1001) {
            onLogout()
        }
    }

    fun isLogin(): Boolean {
        return userInternal.value != null
    }

    fun onLogin(user: User) {
        CommonApp.appScope.launch {
            userInternal.postValue(user)
            AppPreferences.setUser(user)
            updateUserCoin()
        }
    }

    fun onLogout() {
        CommonApp.appScope.launch {
            userInternal.postValue(null)
            AppPreferences.setUser(null)
        }
    }
}