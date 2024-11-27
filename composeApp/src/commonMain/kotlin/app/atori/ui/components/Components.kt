package app.atori.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.atori.resources.Res
import app.atori.resources.app_name
import app.atori.resources.ic_atori_logo_24px
import app.atori.ui.standardIconButtonColors
import app.atori.utils.ResUtils.text
import app.atori.utils.ResUtils.vector
import org.jetbrains.compose.resources.DrawableResource

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
    Box(Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 16.dp), Alignment.BottomStart) {
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
    Switch(checked, onCheckedChange)
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

@Composable
fun TopBarAtoriIcon() = Icon(
    Res.drawable.ic_atori_logo_24px.vector,
    Res.string.app_name.text,
    Modifier.size(24.dp),
    MaterialTheme.colorScheme.onSurfaceVariant
)

@Composable
fun AtoriIconButton(
    icon: DrawableResource, contentDescription: String? = null,
    buttonSize: Int = 40, onClick: () -> Unit = {}
) = AtoriIconButton(onClick, buttonSize) {
    Icon(
        icon.vector,
        contentDescription,
    )
}

@Composable
fun AtoriIconButton(
    onClick: () -> Unit = {}, buttonSize: Int = 48, content: @Composable () -> Unit = {}
) = Button(
    onClick, Modifier.size(buttonSize.dp),
    contentPadding = PaddingValues(0.dp), // Reset padding
    colors = standardIconButtonColors,
) {
    content()
}