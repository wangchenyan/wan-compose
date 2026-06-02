package top.wangchenyan.wancompose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorPalette = darkColorScheme(
    primary = mainColor,
    secondary = mainColor,
    tertiary = mainColor
)

private val LightColorPalette = lightColorScheme(
    primary = mainColor,
    secondary = mainColor,
    tertiary = mainColor
)

@Composable
fun WanandroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val extraColors = if (darkTheme) {
        DarkExtraColors
    } else {
        LightExtraColors
    }

    CompositionLocalProvider(LocalExtraColors provides extraColors) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = content
        )
    }
}
