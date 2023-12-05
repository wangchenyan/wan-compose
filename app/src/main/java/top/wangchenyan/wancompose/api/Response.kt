package top.wangchenyan.wancompose.api

import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by wcy on 2021/4/1.
 */
data class Response<T>(
    @SerializedName("errorCode") var code: Int = 0,
    @SerializedName("errorMsg") var msg: String? = "",
    @SerializedName("data") var data: T? = null
) {
    fun isSuccess(): Boolean {
        return code == CODE_SUCCESS && data != null
    }

    fun isSuccessIgnoreData(): Boolean {
        return code == CODE_SUCCESS
    }

    companion object {
        const val CODE_SUCCESS = 0

        // 网络状态码
        const val CODE_NET_ERROR = 4000
        const val CODE_TIMEOUT = 4080
        const val CODE_JSON_PARSE_ERROR = 4010
        const val CODE_SERVER_ERROR = 5000

        fun <T> fromException(e: Throwable): Response<T> {
            return if (e is HttpException) {
                Response(CODE_NET_ERROR, "网络异常(${e.code()},${e.message()})")
            } else if (e is UnknownHostException) {
                Response(CODE_NET_ERROR, "网络连接失败，请检查后再试")
            } else if (e is ConnectTimeoutException || e is SocketTimeoutException) {
                Response(CODE_TIMEOUT, "请求超时，请稍后再试")
            } else if (e is IOException) {
                Response(CODE_NET_ERROR, "网络异常(${e.message})")
            } else if (e is JsonParseException || e is JSONException) {
                // Json解析失败
                Response(CODE_JSON_PARSE_ERROR, "数据解析错误，请稍后再试")
            } else {
                Response(CODE_SERVER_ERROR, "系统错误(${e.message})")
            }
        }
    }
}