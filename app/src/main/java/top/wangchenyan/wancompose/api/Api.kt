package top.wangchenyan.wancompose.api

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import me.wcy.mockhttp.MockHttpInterceptor
import top.wangchenyan.wancompose.WanApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by wcy on 2021/4/1.
 */
object Api {
    private const val BASE_URL = "https://www.wanandroid.com/"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(MockHttpInterceptor())
            .cookieJar(
                PersistentCookieJar(
                    SetCookieCache(),
                    SharedPrefsCookiePersistor(WanApplication.context)
                )
            )
            .build()
    }

    private val api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(IApi::class.java)
    }

    fun get(): IApi {
        return api
    }
}