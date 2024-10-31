package app.atori.multi

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import app.atori.multi.utils.ComposeUtils.opacify

// 恩情颜色值
val tonalButtonColors
    @Composable
    get() = ButtonColors(
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.onSecondaryContainer,
        MaterialTheme.colorScheme.onSurface.opacify(0.12F),
        MaterialTheme.colorScheme.onSurface
    )
val filledButtonColors
    @Composable
    get() = ButtonColors(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.onSurface.opacify(0.12F),
        MaterialTheme.colorScheme.onSurface
    )

val standardIconButtonColors
    @Composable
    get() = IconButtonColors(
        Color.Transparent,
        MaterialTheme.colorScheme.onSurfaceVariant,
        Color.Transparent,
        MaterialTheme.colorScheme.onSurface.opacify(0.38F)
    )

// 生成的颜色值
val primaryLight = Color(0xFF8D4959)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFFFD9DF)
val onPrimaryContainerLight = Color(0xFF3A0718)
val secondaryLight = Color(0xFF75565C)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFFFD9DF)
val onSecondaryContainerLight = Color(0xFF2B151A)
val tertiaryLight = Color(0xFF7A5732)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFFFDCBC)
val onTertiaryContainerLight = Color(0xFF2C1600)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF410002)
val backgroundLight = Color(0xFFFFF8F7)
val onBackgroundLight = Color(0xFF22191B)
val surfaceLight = Color(0xFFFFF8F7)
val onSurfaceLight = Color(0xFF22191B)
val surfaceVariantLight = Color(0xFFF3DDE0)
val onSurfaceVariantLight = Color(0xFF524345)
val outlineLight = Color(0xFF847375)
val outlineVariantLight = Color(0xFFD6C2C4)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF382E2F)
val inverseOnSurfaceLight = Color(0xFFFEEDEE)
val inversePrimaryLight = Color(0xFFFFB1C1)
val surfaceDimLight = Color(0xFFE7D6D8)
val surfaceBrightLight = Color(0xFFFFF8F7)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFFFF0F1)
val surfaceContainerLight = Color(0xFFFBEAEB)
val surfaceContainerHighLight = Color(0xFFF5E4E6)
val surfaceContainerHighestLight = Color(0xFFEFDEE0)

val primaryDark = Color(0xFFFFB1C1)
val onPrimaryDark = Color(0xFF551D2C)
val primaryContainerDark = Color(0xFF713342)
val onPrimaryContainerDark = Color(0xFFFFD9DF)
val secondaryDark = Color(0xFFE4BDC3)
val onSecondaryDark = Color(0xFF43292E)
val secondaryContainerDark = Color(0xFF5B3F45)
val onSecondaryContainerDark = Color(0xFFFFD9DF)
val tertiaryDark = Color(0xFFECBE91)
val onTertiaryDark = Color(0xFF462A09)
val tertiaryContainerDark = Color(0xFF60401D)
val onTertiaryContainerDark = Color(0xFFFFDCBC)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF191113)
val onBackgroundDark = Color(0xFFEFDEE0)
val surfaceDark = Color(0xFF191113)
val onSurfaceDark = Color(0xFFEFDEE0)
val surfaceVariantDark = Color(0xFF524345)
val onSurfaceVariantDark = Color(0xFFD6C2C4)
val outlineDark = Color(0xFF9F8C8F)
val outlineVariantDark = Color(0xFF524345)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFEFDEE0)
val inverseOnSurfaceDark = Color(0xFF382E2F)
val inversePrimaryDark = Color(0xFF8D4959)
val surfaceDimDark = Color(0xFF191113)
val surfaceBrightDark = Color(0xFF413738)
val surfaceContainerLowestDark = Color(0xFF140C0E)
val surfaceContainerLowDark = Color(0xFF22191B)
val surfaceContainerDark = Color(0xFF261D1F)
val surfaceContainerHighDark = Color(0xFF312829)
val surfaceContainerHighestDark = Color(0xFF3C3234)

val testMultiDarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

val testMultiLightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

// 包装的一层主题，你可以理解为在毛坯房上刷油漆
@Composable
fun AtoriMultiTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) testMultiDarkColorScheme
    else testMultiLightColorScheme

    MaterialTheme(
        colorScheme = colors, // 颜色
        content = content // 声明的视图
    )
}