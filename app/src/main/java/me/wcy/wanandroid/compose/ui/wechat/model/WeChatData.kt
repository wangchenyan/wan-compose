package me.wcy.wanandroid.compose.ui.wechat.model

import com.google.gson.annotations.SerializedName

data class WeChatAuthor(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("name") val name: String = ""
)