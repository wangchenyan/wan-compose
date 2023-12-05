package top.wangchenyan.wancompose.storage

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import top.wangchenyan.wancompose.utils.GsonUtils
import java.io.IOException

/**
 * Created by wcy on 2021/2/25.
 */
class PreferencesDataStore(context: Context, name: String) {
    private val ds by lazy {
        PreferenceDataStoreFactory.create {
            context.applicationContext.preferencesDataStoreFile(name)
        }
    }

    suspend fun <T> remove(key: Preferences.Key<T>) =
        ds.edit { it.remove(key) }

    suspend fun clear() =
        ds.edit { it.clear() }

    suspend inline fun <reified T> get(key: String): T = get(preferencesKey(key))

    suspend inline fun <reified T> get(key: Preferences.Key<T>): T =
        get(
            key, when (T::class.java) {
                Boolean::class.javaObjectType -> false as T
                Int::class.javaObjectType -> 0 as T
                Long::class.javaObjectType -> 0L as T
                Float::class.javaObjectType -> 0F as T
                Double::class.javaObjectType -> 0.0 as T
                String::class.javaObjectType -> "" as T
                else -> throw IllegalArgumentException("不支持的类型")
            }
        )

    suspend fun <T> get(key: Preferences.Key<T>, defValue: T): T =
        ds.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[key] ?: defValue
            }.first()

    suspend inline fun <reified T> put(key: String, value: T) = put(preferencesKey(key), value)

    suspend fun <T> put(key: Preferences.Key<T>, value: T) = ds.edit { it[key] = value }

    suspend inline fun <reified T> getModel(key: Preferences.Key<String>): T? {
        val json = get(key)
        if (json.isNotEmpty()) {
            return GsonUtils.fromJson(json)
        }
        return null
    }

    suspend inline fun <reified T> putModel(key: Preferences.Key<String>, t: T) {
        if (t == null) {
            remove(key)
        } else {
            put(key, GsonUtils.toJson(t))
        }
    }

    suspend inline fun <reified T> getList(key: Preferences.Key<String>): List<T>? {
        val json = get(key)
        if (json.isNotEmpty()) {
            return GsonUtils.fromJsonList(json)
        }
        return null
    }

    suspend inline fun <reified T> putList(key: Preferences.Key<String>, list: List<T>) =
        putModel(key, list)

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> preferencesKey(key: String): Preferences.Key<T> =
        when (T::class.java) {
            Boolean::class.javaObjectType -> booleanPreferencesKey(key) as Preferences.Key<T>
            Int::class.javaObjectType -> intPreferencesKey(key) as Preferences.Key<T>
            Long::class.javaObjectType -> longPreferencesKey(key) as Preferences.Key<T>
            Float::class.javaObjectType -> floatPreferencesKey(key) as Preferences.Key<T>
            Double::class.javaObjectType -> doublePreferencesKey(key) as Preferences.Key<T>
            String::class.javaObjectType -> stringPreferencesKey(key) as Preferences.Key<T>
            else -> throw IllegalArgumentException("不支持的类型")
        }
}