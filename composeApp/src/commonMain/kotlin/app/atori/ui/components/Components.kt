package app.atori.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import app.atori.resources.Res
import app.atori.resources.app_name
import app.atori.resources.ic_atori_logo_24px
import app.atori.ui.atoriAcceptColor
import app.atori.ui.atoriDenyColor
import app.atori.ui.atoriIconButtonFilledColor
import app.atori.ui.atoriIconButtonSpecialErrorColor
import app.atori.ui.atoriIconButtonSpecialNotActivatedColor
import app.atori.ui.atoriIconButtonStandardColor
import app.atori.utils.ComposeUtils.buttonColors
import app.atori.utils.ResUtils.text
import app.atori.utils.ResUtils.vector
import org.jetbrains.compose.resources.DrawableResource

// 输入框

@Composable
fun SimpleTextField(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurface,
    placeholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    BasicTextField(
        text,
        onTextChange,
        modifier,
        cursorBrush = SolidColor(color),
        textStyle = style.merge(color),
        decorationBox = { innerTextField ->
            if (text.isEmpty()) Text(
                placeholder, color = placeholderColor,
                style = style, maxLines = 1
            )
            innerTextField()
        }
    )
}

// 偏好相关

@Composable
fun PreferenceGroup(
    title: String,
    content: @Composable () -> Unit = {}
) = Column(Modifier.padding(horizontal = 8.dp)) {
    PreferenceHeader(title)
    content()
}

@Composable
fun PreferenceHeader(title: String) =
    Box(Modifier.fillMaxWidth().height(48.dp).padding(16.dp, 4.dp), Alignment.BottomStart) {
        Text(
            title, style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }

@Composable
fun SwitchPreferenceItem(
    icon: DrawableResource,
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) = PreferenceItem(icon, title, subtitle, { onCheckedChange(!checked) }
) {
    AtoriSwitch(checked, onCheckedChange)
}

@Composable
fun PreferenceItem(
    icon: DrawableResource,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) = Row(
    Modifier.fillMaxWidth().clip(MaterialTheme.shapes.large)
        .clickable(onClick = onClick).padding(16.dp, 12.dp),
    Arrangement.spacedBy(16.dp), Alignment.CenterVertically
) {
    Icon(icon.vector, title, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    Column(Modifier.weight(1F)) {
        Text(
            title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (subtitle != null) Text(
            subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    content()
}

// 反应相关

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IconStringChip(
    selected: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) = Row(
    Modifier.height(32.dp).clip(MaterialTheme.shapes.small).clickable(onClick = onClick)
        .background(if (selected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent)
        .border(
            1.dp,
            if (selected) Color.Transparent else MaterialTheme.colorScheme.outline,
            MaterialTheme.shapes.small
        )
        .padding(8.dp, 0.dp, 16.dp, 0.dp), Arrangement.spacedBy(8.dp), Alignment.CenterVertically
) {
    LocalTextStyle provides MaterialTheme.typography.labelLarge
    LocalContentColor provides if (selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    content()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IconChip(icon: ImageVector, description: String? = null, onClick: () -> Unit = {}) = Box(
    Modifier.height(32.dp).clip(MaterialTheme.shapes.small).clickable(onClick = onClick)
        .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small)
        .padding(8.dp, 0.dp), Alignment.Center
) {
    Icon(
        icon,
        description,
        tint = MaterialTheme.colorScheme.primary,
    )
}

// Atori图标

@Composable
fun PrefabAtoriLogoIcon() = Icon(
    Res.drawable.ic_atori_logo_24px.vector,
    Res.string.app_name.text,
    Modifier.size(24.dp),
    MaterialTheme.colorScheme.onSurfaceVariant
)

// 自定义图标按钮

object AtoriIconButtonDefaults {
    const val ButtonSize = 40
    val Style
        @Composable
        get() = AtoriIconButtonStyles.Standard
}

object AtoriIconButtonStyles {
    val SpecialNotActivated
        @Composable
        get() = atoriIconButtonSpecialNotActivatedColor
    val SpecialDeny
        @Composable
        get() = atoriDenyColor.buttonColors
    val SpecialAccept
        @Composable
        get() = atoriAcceptColor.buttonColors
    val Filled
        @Composable
        get() = atoriIconButtonFilledColor
    val Standard
        @Composable
        get() = atoriIconButtonStandardColor
    val Outlined
        @Composable
        get() = ButtonDefaults.outlinedButtonColors()
    val Tonal
        @Composable
        get() = ButtonDefaults.filledTonalButtonColors()
}

@Composable
fun AtoriIconButton(
    icon: DrawableResource,
    contentDescription: String? = null,
    buttonSize: Int = AtoriIconButtonDefaults.ButtonSize,
    style: ButtonColors = AtoriIconButtonDefaults.Style,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) = AtoriIconButton(onClick, buttonSize, style, modifier) {
    Icon(
        icon.vector,
        contentDescription,
    )
}

@Composable
fun AtoriIconButton(
    onClick: () -> Unit = {},
    buttonSize: Int = AtoriIconButtonDefaults.ButtonSize,
    style: ButtonColors = AtoriIconButtonDefaults.Style,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) = Button(
    onClick, modifier.size(buttonSize.dp),
    contentPadding = PaddingValues(0.dp), // Reset padding
    colors = style
) {
    content()
}

// 简化

@Composable
fun AtoriMenuItem(title: String, icon: DrawableResource, onClick: () -> Unit = {}) =
    DropdownMenuItem({ Text(title) }, onClick, Modifier, {
        Icon(icon.vector, title)
    })

@Composable
fun AtoriSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) =
    Switch(checked, onCheckedChange, Modifier.height(32.dp))