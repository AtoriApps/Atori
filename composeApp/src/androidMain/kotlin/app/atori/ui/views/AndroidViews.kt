package app.atori.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.atori.misc.DemoConstants
import app.atori.resources.Res
import app.atori.resources.back
import app.atori.resources.ic_back_24px
import app.atori.resources.ic_more_24px
import app.atori.resources.last_seen_recently
import app.atori.resources.more
import app.atori.resources.online
import app.atori.ui.components.AtoriIconButton
import app.atori.utils.ResUtils.text


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScreensTopBar(
    title: String = DemoConstants.userName,
    subtitle: String = "${Res.string.online.text} • ${Res.string.last_seen_recently.text}",
    showSubtitle: Boolean = true,
    showMoreButton: Boolean = true,
    showBackButton: Boolean = true,
    showDropMenu: Boolean = false,
    onDismissDropMenu: () -> Unit = {},
    dropMenuContent: (@Composable () -> Unit)? = null,
    onClickTitle: (() -> Unit)? = null,
    onClickMore: () -> Unit = {},
    onClickBack: () -> Unit = {}
) = Row(
    Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surfaceContainer)
        .windowInsetsPadding(TopAppBarDefaults.windowInsets)
        .height(64.dp)
        .padding(horizontal = 8.dp),
    Arrangement.spacedBy(8.dp), Alignment.CenterVertically
) {
    // 左侧内容
    if (showBackButton)
        AtoriIconButton(Res.drawable.ic_back_24px, Res.string.back.text, 48, onClick = onClickBack)

    // 中间标题
    Column(
        Modifier
            .weight(1F)
            .clickable(null, null, onClickTitle != null, onClick = onClickTitle ?: {})
    ) {
        Text(
            title, color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        if (showSubtitle) Text(
            subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge
        )
    }

    // 右侧内容
    if (showMoreButton) Box {
        AtoriIconButton(Res.drawable.ic_more_24px, Res.string.more.text, 48, onClick = onClickMore)
        if (dropMenuContent != null) DropdownMenu(
            showDropMenu,
            onDismissDropMenu
        ) { dropMenuContent() }
    }
}