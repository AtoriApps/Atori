package app.atori.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.atori.standardIconButtonColors
import app.atori.utils.ResUtils.vector
import app.atori.resources.Res
import app.atori.resources.app_name
import app.atori.resources.ic_atori_logo_24px
import app.atori.utils.ResUtils.imgBmp
import app.atori.utils.ResUtils.text
import jdk.jfr.Description
import org.jetbrains.compose.resources.DrawableResource

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
fun TopBarControlButton(
    icon: DrawableResource, contentDescription: String, onClick: () -> Unit = {}
) = TopBarControlButton(onClick) {
    Icon(
        icon.vector,
        contentDescription,
    )
}

@Composable
fun TopBarControlButton(onClick: () -> Unit = {}, content: @Composable () -> Unit = {}) = Button(
    onClick, Modifier.size(48.dp),
    contentPadding = PaddingValues(0.dp),
    colors = standardIconButtonColors,
) {
    content()
}