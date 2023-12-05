package top.wangchenyan.wancompose.ui.wechat.model

import com.google.gson.annotations.SerializedName

data class WeChatAuthor(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("name") val name: String = ""
)