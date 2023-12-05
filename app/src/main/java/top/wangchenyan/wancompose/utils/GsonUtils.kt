package top.wangchenyan.wancompose.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser

/**
 * Created by wcy on 2021/2/24.
 */
object GsonUtils {
    val gson by lazy { Gson() }
    val parser by lazy { JsonParser() }

    fun toJson(src: Any): String {
        return gson.toJson(src)
    }

    inline fun <reified T> fromJson(json: String): T? {
        return fromJson(json, T::class.java)
    }

    fun <T> fromJson(json: String, clazz: Class<T>): T? {
        try {
            return gson.fromJson(json, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T> fromJson(json: JsonElement): T? {
        return fromJson(json, T::class.java)
    }

    fun <T> fromJson(json: JsonElement, clazz: Class<T>): T? {
        try {
            return gson.fromJson(json, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T> fromJsonList(json: String): List<T>? {
        return fromJsonList(json, T::class.java)
    }

    fun <T> fromJsonList(json: String, clazz: Class<T>): List<T>? {
        try {
            val list = mutableListOf<T>()
            val jsonArray = parser.parse(json).asJsonArray
            for (item in jsonArray) {
                val bean = gson.fromJson(item, clazz)
                list.add(bean)
            }
            return list
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}