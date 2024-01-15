package top.wangchenyan.wancompose.ui.search.model

import com.google.gson.annotations.SerializedName

data class HotKey(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("link") val link: String = "",
    @SerializedName("name") val name: String = ""
)