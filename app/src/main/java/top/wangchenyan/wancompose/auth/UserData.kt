package top.wangchenyan.wancompose.auth

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("username") val username: String = "",
    @SerializedName("nickname") val nickname: String = "",
    @SerializedName("chapterTops") val chapterTops: List<Any> = listOf(),
    @SerializedName("coinCount") val coinCount: Int = 0,
    @SerializedName("collectIds") val collectIds: List<Int> = listOf(),
    @SerializedName("email") val email: String = "",
    @SerializedName("icon") val icon: String = "",
    @SerializedName("level") val level: String = "",
    @SerializedName("rank") val rank: String = ""
)