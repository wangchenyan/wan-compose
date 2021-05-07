package me.wcy.wanandroid.compose.api

import me.wcy.wanandroid.compose.model.HomeBannerData
import me.wcy.wanandroid.compose.model.HomeArticle
import me.wcy.wanandroid.compose.model.HomeArticleList
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by wcy on 2021/4/1.
 */
interface IApi {
    @GET("banner/json")
    suspend fun getHomeBanner(): Response<List<HomeBannerData>>

    @GET("article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int = 0): Response<HomeArticleList>

    @GET("article/top/json")
    suspend fun getStickyArticle(): Response<List<HomeArticle>>
}