package app.atori.ui

import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
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
    val (lightColors, darkColors) = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S && useDynamicColors)
        dynamicLightColorScheme(context) to dynamicDarkColorScheme(context)
    else atoriLightColorScheme to atoriDarkColorScheme

    AtoriTheme(useDarkTheme, darkColors, lightColors, content)
}