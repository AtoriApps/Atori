package app.atori.ui

import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// TODO: 联动偏好设置
@Composable
fun AndroidAtoriTheme(
    context: Context = LocalContext.current, // 提供一个上下文以弄动态取色
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S && useDynamicColors) {
        if (useDarkTheme) dynamicDarkColorScheme(context)
        else dynamicLightColorScheme(context)
    } else {
        // Fallback to static colors
        if (useDarkTheme) atoriDarkColorScheme
        else atoriLightColorScheme
    }

    MaterialTheme(
        colorScheme = colors, // 颜色
        content = content // 声明的视图
    )
}