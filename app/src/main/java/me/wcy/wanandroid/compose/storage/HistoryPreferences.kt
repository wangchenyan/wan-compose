package me.wcy.wanandroid.compose.storage

import androidx.datastore.preferences.core.stringPreferencesKey
import me.wcy.wanandroid.compose.WanApplication

object HistoryPreferences {
    private val KEY_HISTORY = stringPreferencesKey("history")

    private val dataStore by lazy {
        PreferencesDataStore(
            WanApplication.context,
            WanApplication.context.packageName + ".history"
        )
    }

    suspend fun getHistory(): List<String> {
        return dataStore.getList(KEY_HISTORY) ?: listOf()
    }

    suspend fun addHistory(item: String) {
        if (item.isEmpty()) {
            return
        }
        val list = getHistory().toMutableList()
        if (list.contains(item)) {
            return
        }
        list.add(0, item)
        dataStore.putList(KEY_HISTORY, list)
    }

    suspend fun removeHistory(item: String) {
        if (item.isEmpty()) {
            return
        }
        val list = getHistory().toMutableList()
        if (!list.contains(item)) {
            return
        }
        list.remove(item)
        dataStore.putList(KEY_HISTORY, list)
    }
}