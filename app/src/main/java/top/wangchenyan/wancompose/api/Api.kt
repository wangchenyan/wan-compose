package top.wangchenyan.wancompose.api

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import me.wcy.mockhttp.MockHttpInterceptor
import me.wcy.wanandroid.compose.ui.search.HotKey
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.*
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.net.NetResult
import top.wangchenyan.common.net.gson.GsonConverterFactory
import top.wangchenyan.common.utils.GsonUtils
import top.wangchenyan.wancompose.auth.User
import top.wangchenyan.wancompose.ui.home.model.Article
import top.wangchenyan.wancompose.ui.home.model.ArticleList
import top.wangchenyan.wancompose.ui.home.model.HomeBannerData
import top.wangchenyan.wancompose.ui.wechat.model.WeChatAuthor
import java.util.concurrent.TimeUnit

/**
 * Created by wcy on 2021/4/1.
 */
interface Api {
    @GET("banner/json")
    suspend fun getHomeBanner(): NetResult<List<HomeBannerData>>

    @GET("article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int = 0): NetResult<ArticleList>

    @GET("article/top/json")
    suspend fun getStickyArticle(): NetResult<List<Article>>

    @GET("user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int = 0): NetResult<ArticleList>

    @GET("wxarticle/chapters/json")
    suspend fun getWeChatAuthorList(): NetResult<List<WeChatAuthor>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWeChatArticleList(
        @Path("id") id: Long,
        @Path("page") page: Int = 0
    ): NetResult<ArticleList>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): NetResult<User>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): NetResult<String>

    @GET("user/logout/json")
    suspend fun logout(): NetResult<String>

    @GET("lg/coin/userinfo/json")
    suspend fun getUserCoin(): NetResult<User>

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectArticleList(@Path("page") page: Int = 0): NetResult<ArticleList>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Long): NetResult<String>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: Long): NetResult<String>

    @GET("hotkey/json")
    suspend fun searchHotKey(): NetResult<List<HotKey>>

    @POST("article/query/{page}/json")
    suspend fun search(
        @Path("page") page: Int,
        @Query("k") keyword: String,
    ): NetResult<ArticleList>

    companion object {
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
                        SharedPrefsCookiePersistor(CommonApp.app)
                    )
                )
                .build()
        }

        private val api by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create(GsonUtils.gson, true)
                )
                .client(okHttpClient)
                .build()
            retrofit.create(Api::class.java)
        }

        fun get(): Api {
            return api
        }
    }
}