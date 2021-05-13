package me.wcy.wanandroid.compose.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.wcy.wanandroid.compose.R
import me.wcy.wanandroid.compose.theme.Colors

/**
 * Created by wcy on 2021/3/31.
 */

@Composable
fun TitleBar(
    title: String,
    onBack: (() -> Unit)? = null,
    @DrawableRes icon: Int? = null,
    onIconClick: (() -> Unit)? = null,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Colors.titleBar)
    ) {
        if (onBack != null) {
            Icon(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        onBack.invoke()
                    }
                    .size(48.dp)
                    .padding(14.dp),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "返回",
                tint = Colors.text_h1
            )
        }
        Text(
            text = title,
            Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (icon != null) {
            Icon(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        onIconClick?.invoke()
                    }
                    .size(48.dp)
                    .padding(14.dp),
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = Colors.text_h1
            )
        }
    }
}