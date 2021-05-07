package me.wcy.wanandroid.compose.api

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by wcy on 2021/4/1.
 */
object ApiCaller {
    const val TAG = "ApiCaller"
}

suspend inline fun <T> apiCall(crossinline call: suspend CoroutineScope.() -> Response<T>): Response<T> {
    return withContext(Dispatchers.IO) {
        val res: Response<T>
        try {
            res = call()
        } catch (e: Throwable) {
            Log.e(ApiCaller.TAG, "request error", e)
            return@withContext Response.fromException(e)
        }
        return@withContext res
    }
}