package me.wcy.wanandroid.compose.model

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import me.wcy.wanandroid.compose.ui.theme.Colors


/**
 * Created by wcy on 2021/4/1.
 */

data class HomeBannerData(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("desc") val desc: String = "",
    @SerializedName("imagePath") val imagePath: String = "",
    @SerializedName("isVisible") val isVisible: Int = 0,
    @SerializedName("order") val order: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("type") val type: Int = 0,
    @SerializedName("url") val url: String = ""
)

data class HomeArticleList(
    @SerializedName("curPage") val curPage: Int = 0,
    @SerializedName("datas") val datas: List<HomeArticle> = listOf(),
    @SerializedName("offset") val offset: Int = 0,
    @SerializedName("over") val over: Boolean = false,
    @SerializedName("pageCount") val pageCount: Int = 0,
    @SerializedName("size") val size: Int = 0,
    @SerializedName("total") val total: Int = 0
)

data class HomeArticle(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("apkLink") val apkLink: String = "",
    @SerializedName("audit") val audit: Int = 0,
    @SerializedName("author") private val author: String = "",
    @SerializedName("canEdit") val canEdit: Boolean = false,
    @SerializedName("chapterId") val chapterId: Int = 0,
    @SerializedName("chapterName") val chapterName: String = "",
    @SerializedName("collect") val collect: Boolean = false,
    @SerializedName("courseId") val courseId: Int = 0,
    @SerializedName("desc") val desc: String = "",
    @SerializedName("descMd") val descMd: String = "",
    @SerializedName("envelopePic") val envelopePic: String = "",
    @SerializedName("fresh") val fresh: Boolean = false,
    @SerializedName("host") val host: String = "",
    @SerializedName("link") val link: String = "",
    @SerializedName("niceDate") val niceDate: String = "",
    @SerializedName("niceShareDate") val niceShareDate: String = "",
    @SerializedName("origin") val origin: String = "",
    @SerializedName("prefix") val prefix: String = "",
    @SerializedName("projectLink") val projectLink: String = "",
    @SerializedName("publishTime") val publishTime: Long = 0,
    @SerializedName("realSuperChapterId") val realSuperChapterId: Int = 0,
    @SerializedName("selfVisible") val selfVisible: Int = 0,
    @SerializedName("shareDate") val shareDate: Long = 0,
    @SerializedName("shareUser") val shareUser: String = "",
    @SerializedName("superChapterId") val superChapterId: Int = 0,
    @SerializedName("superChapterName") val superChapterName: String = "",
    @SerializedName("tags") val tags: MutableList<HomeArticleTag> = mutableListOf(),
    @SerializedName("title") val title: String = "",
    @SerializedName("type") val type: Int = 0,
    @SerializedName("userId") val userId: Int = 0,
    @SerializedName("visible") val visible: Int = 0,
    @SerializedName("zan") val zan: Int = 0
) {
    fun getAuthor(): String {
        return if (author.isNotEmpty()) author else shareUser
    }
}

data class HomeArticleTag(
    @SerializedName("name") val name: String = "",
    @SerializedName("url") val url: String = ""
) {
    fun getColor(): Color {
        return when (name) {
            "置顶" -> Color(0xFFF44336)
            "本站发布" -> Color(0xFF2196F3)
            "问答" -> Color(0xFF00BCD4)
            "公众号" -> Color(0xFF4CAF50)
            "项目" -> Color(0xFF009688)
            else -> Colors.main
        }
    }
}