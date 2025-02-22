package app.atori.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import app.atori.utils.MultiplatformLogUtils
import androidx.compose.ui.graphics.Color
import app.atori.utils.ComposeUtils.opacity

val LocalUseDarkTheme = compositionLocalOf { false }

data class ColorSet(
    val color: Color = Color.Unspecified,
    val onColor: Color = Color.Unspecified,
    val colorContainer: Color = Color.Unspecified,
    val onColorContainer: Color = Color.Unspecified,
    val colorName: String = "Color"
)

// 恩情颜色值
val atoriIconButtonSpecialNotActivatedColor
    @Composable
    get() = ButtonColors(
        MaterialTheme.colorScheme.surfaceContainerLow,
        MaterialTheme.colorScheme.onSurface,
        MaterialTheme.colorScheme.surfaceContainerLowest,
        MaterialTheme.colorScheme.onSurface.opacity(0.38F)
    )

val atoriIconButtonSpecialErrorColor
    @Composable
    get() = ButtonColors(
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.onError,
        MaterialTheme.colorScheme.errorContainer,
        MaterialTheme.colorScheme.onError.opacity(0.38F)
    )

val atoriIconButtonFilledColor
    @Composable
    get() = ButtonColors(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.onSurface.opacity(0.12F),
        MaterialTheme.colorScheme.onSurface.opacity(0.38F)
    )

val atoriIconButtonStandardColor
    @Composable
    get() = ButtonColors(
        Color.Transparent,
        MaterialTheme.colorScheme.onSurfaceVariant,
        Color.Transparent,
        MaterialTheme.colorScheme.onSurface.opacity(0.38F)
    )

val atoriAcceptColor: ColorSet
    @Composable
    get() = if (LocalUseDarkTheme.current) ColorSet(
        acceptDark, onAcceptDark, acceptContainerDark, onAcceptContainerDark, "Accept"
    )
    else ColorSet(
        acceptLight, onAcceptLight, acceptContainerLight, onAcceptContainerLight, "Accept"
    )

val atoriDenyColor
    @Composable
    get() = if (LocalUseDarkTheme.current) ColorSet(
        denyDark, onDenyDark, denyContainerDark, onDenyContainerDark, "Deny"
    )
    else ColorSet(
        denyLight, onDenyLight, denyContainerLight, onDenyContainerLight, "Deny"
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
val denyLight = Color(0xFF904B40)
val onDenyLight = Color(0xFFFFFFFF)
val denyContainerLight = Color(0xFFFFDAD4)
val onDenyContainerLight = Color(0xFF3A0905)
val acceptLight = Color(0xFF406836)
val onAcceptLight = Color(0xFFFFFFFF)
val acceptContainerLight = Color(0xFFC0EFB0)
val onAcceptContainerLight = Color(0xFF002200)

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
val denyDark = Color(0xFFFFB4A8)
val onDenyDark = Color(0xFF561E16)
val denyContainerDark = Color(0xFF73342A)
val onDenyContainerDark = Color(0xFFFFDAD4)
val acceptDark = Color(0xFFA5D395)
val onAcceptDark = Color(0xFF11380B)
val acceptContainerDark = Color(0xFF285020)
val onAcceptContainerDark = Color(0xFFC0EFB0)

val atoriDarkColorScheme = darkColorScheme(
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

val atoriLightColorScheme = lightColorScheme(
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
fun AtoriTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(), // Rewrite LocalIsDarkTheme
    darkColorScheme: ColorScheme = atoriDarkColorScheme,
    lightColorScheme: ColorScheme = atoriLightColorScheme,
    content: @Composable () -> Unit
) = CompositionLocalProvider(LocalUseDarkTheme provides useDarkTheme) {
    MultiplatformLogUtils.d("重组AtoriTheme：${useDarkTheme} ${LocalUseDarkTheme.current}")

    val colors = if (useDarkTheme) darkColorScheme
    else lightColorScheme

    MaterialTheme(
        colorScheme = colors, // 颜色
        content = content // 声明的视图
    )
}