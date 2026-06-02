package top.wangchenyan.wancompose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val mainColor = Color(0xFF4CAF50)

@Immutable
data class WanandroidExtraColors(
    val red: Color = Color(0xFFF44336),
    val blue: Color = Color(0xFF2196F3),
    val main: Color = mainColor,
    val bg: Color,
    val bgOverlay: Color,
    val unselect: Color,
    val titleBar: Color,
    val bottomBar: Color,
    val textH1: Color,
    val textH2: Color
)

internal val LightExtraColors = WanandroidExtraColors(
    bg = Color(0xFFF4F4F4),
    bgOverlay = Color.White,
    unselect = Color(0xFF424242),
    titleBar = Color(0xFFFAFAFA),
    bottomBar = Color(0xFFFAFAFA),
    textH1 = Color(0xFF212121),
    textH2 = Color(0xFF757575)
)

internal val DarkExtraColors = WanandroidExtraColors(
    bg = Color(0xFF121212),
    bgOverlay = Color.Black,
    unselect = Color(0xFF9E9E9E),
    titleBar = Color(0xFF1C1C1E),
    bottomBar = Color(0xFF1C1C1E),
    textH1 = Color(0xFFF5F5F5),
    textH2 = Color(0xFFB0B0B0)
)

internal val LocalExtraColors = staticCompositionLocalOf {
    LightExtraColors
}

object AppTheme {
    val colors: WanandroidExtraColors
        @Composable
        get() = LocalExtraColors.current
}
