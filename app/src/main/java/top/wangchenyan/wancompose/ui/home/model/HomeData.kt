package top.wangchenyan.wancompose.ui.home.model

import com.google.gson.annotations.SerializedName


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
