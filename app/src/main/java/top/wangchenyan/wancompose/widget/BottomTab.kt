package top.wangchenyan.wancompose.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.wangchenyan.wancompose.R
import top.wangchenyan.wancompose.theme.Colors

/**
 * Created by wcy on 2021/3/30.
 */

@Preview
@Composable
fun BottomTabPreview() {
    BottomTab(current = 0) {
    }
}

@Composable
fun BottomTab(
    current: Int,
    currentChanged: (Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Colors.bottomBar)
            .padding(4.dp, 0.dp)
    ) {
        TabItem(
            Modifier
                .weight(1f)
                .clickable {
                    currentChanged(0)
                },
            if (current == 0) R.drawable.ic_tab_home_fill else R.drawable.ic_tab_home,
            "首页",
            if (current == 0) Colors.main else Colors.unselect
        )
        TabItem(
            Modifier
                .weight(1f)
                .clickable {
                    currentChanged(1)
                },
            if (current == 1) R.drawable.ic_tab_discover_fill else R.drawable.ic_tab_discover,
            "广场",
            if (current == 1) Colors.main else Colors.unselect
        )
        TabItem(
            Modifier
                .weight(1f)
                .clickable {
                    currentChanged(2)
                },
            if (current == 2) R.drawable.ic_tab_wechat_fill else R.drawable.ic_tab_wechat,
            "公众号",
            if (current == 2) Colors.main else Colors.unselect
        )
        TabItem(
            Modifier
                .weight(1f)
                .clickable {
                    currentChanged(3)
                },
            if (current == 3) R.drawable.ic_tab_my_fill else R.drawable.ic_tab_my,
            "我的",
            if (current == 3) Colors.main else Colors.unselect
        )
    }
}

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    title: String,
    tint: Color
) {
    Column(
        modifier.padding(0.dp, 8.dp, 0.dp, 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painterResource(iconResId), title, Modifier.size(24.dp), tint)
        Text(title, fontSize = 11.sp, color = tint)
    }
}