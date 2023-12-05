package top.wangchenyan.wancompose.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.wangchenyan.wancompose.api.Api
import top.wangchenyan.wancompose.api.apiCall
import top.wangchenyan.wancompose.storage.AppPreferences

object AuthManager {
    private var userInternal = MutableLiveData<User>()
    val user: LiveData<User> = userInternal

    fun init() {
        GlobalScope.launch {
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
        if (userRes.isSuccess()) {
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
        GlobalScope.launch {
            userInternal.postValue(user)
            AppPreferences.setUser(user)
            updateUserCoin()
        }
    }

    fun onLogout() {
        GlobalScope.launch {
            userInternal.postValue(null)
            AppPreferences.setUser(null)
        }
    }
}