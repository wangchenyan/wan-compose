package top.wangchenyan.wancompose.storage

import androidx.datastore.preferences.core.stringPreferencesKey
import top.wangchenyan.android.common.CommonApp
import top.wangchenyan.wancompose.auth.User

object AppPreferences {
    private val KEY_USER = stringPreferencesKey("user")

    private val dataStore by lazy {
        PreferencesDataStore(CommonApp.app, CommonApp.app.packageName + ".app")
    }

    suspend fun getUser(): User? {
        return dataStore.getModel(KEY_USER)
    }

    suspend fun setUser(user: User?) {
        dataStore.putModel(KEY_USER, user)
    }
}