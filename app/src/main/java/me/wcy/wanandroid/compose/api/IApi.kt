package me.wcy.wanandroid.compose.api

import me.wcy.wanandroid.compose.auth.User
import me.wcy.wanandroid.compose.ui.home.model.Article
import me.wcy.wanandroid.compose.ui.home.model.ArticleList
import me.wcy.wanandroid.compose.ui.home.model.HomeBannerData
import me.wcy.wanandroid.compose.ui.wechat.model.WeChatAuthor
import retrofit2.http.*

/**
 * Created by wcy on 2021/4/1.
 */
interface IApi {
    @GET("banner/json")
    suspend fun getHomeBanner(): Response<List<HomeBannerData>>

    @GET("article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int = 0): Response<ArticleList>

    @GET("article/top/json")
    suspend fun getStickyArticle(): Response<List<Article>>

    @GET("user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int = 0): Response<ArticleList>

    @GET("wxarticle/chapters/json")
    suspend fun getWeChatAuthorList(): Response<List<WeChatAuthor>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWeChatArticleList(
        @Path("id") id: Long,
        @Path("page") page: Int = 0
    ): Response<ArticleList>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<User>

    @GET("user/logout/json")
    suspend fun logout(): Response<String>

    @GET("lg/coin/userinfo/json")
    suspend fun getUserCoin(): Response<User>

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectArticleList(@Path("page") page: Int = 0): Response<ArticleList>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Long): Response<String>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: Long): Response<String>
}