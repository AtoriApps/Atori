package app.atori.ui.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import app.atori.misc.DemoData
import app.atori.models.NavTabItem
import app.atori.ui.pages.DemoChatInfoPage
import app.atori.ui.pages.EmptyPage
import app.atori.ui.standardIconButtonColors
import app.atori.stores.DesktopAppStateStore
import app.atori.utils.ResUtils.vector
import app.atori.resources.*
import app.atori.resources.Res
import app.atori.stores.UniversalStateStore
import app.atori.utils.ResUtils.text
import app.atori.ui.components.AtoriIconButton
import app.atori.ui.views.DemoChatInputBar
import app.atori.ui.views.DemoChatView

@Composable
fun SidePanel() {
    @Composable
    fun ItemButton(
        sidePanelItem: NavTabItem,
        isCurrent: Boolean = false,
        onClick: () -> Unit = {}
    ) {
        Button(
            onClick,
            Modifier.size(48.dp),
            contentPadding = PaddingValues(0.dp),
            colors = if (isCurrent) ButtonDefaults.filledTonalButtonColors() else standardIconButtonColors
        ) {
            Icon(
                (if (isCurrent) sidePanelItem.selectedIcon else sidePanelItem.icon).vector,
                sidePanelItem.name
            )
        }
    }

    Row(Modifier.fillMaxHeight()) {
        Column(
            Modifier.fillMaxHeight().width(64.dp).padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 顶排按钮
            Column(Modifier.weight(1F), Arrangement.spacedBy(4.dp)) {
                UniversalStateStore.navTabItems.forEachIndexed { index, tab ->
                    ItemButton(tab, index == UniversalStateStore.currentNavTab.value) {
                        UniversalStateStore.currentNavTab.value = index
                    }
                }
            }
            // 底排按钮
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Button(
                    {
                        DesktopAppStateStore.sidePanelExpanded.value =
                            !DesktopAppStateStore.sidePanelExpanded.value
                    },
                    Modifier.size(48.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = standardIconButtonColors
                ) {
                    Icon(
                        (if (DesktopAppStateStore.sidePanelExpanded.value) Res.drawable.ic_panel_narrow_24px else Res.drawable.ic_panel_24px).vector,
                        "展开或收起侧面板"
                    )
                }
            }
        }
        // TODO:当前面板容器
        if (DesktopAppStateStore.sidePanelExpanded.value) {
            Box(
                Modifier.padding(end = 8.dp).width(300.dp).fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                UniversalStateStore.navTabItems[UniversalStateStore.currentNavTab.value].view()
            }
        }
    }
}

@Composable
fun DemoChatPanel() {
    val strokeColor = MaterialTheme.colorScheme.outlineVariant
    var showInfoPanel by remember { mutableStateOf(false) }

    @Composable
    fun DemoChatPanelTopBar() = Row(
        Modifier.height(64.dp).fillMaxWidth().drawWithCache {
            val strokeWidth = 1.dp.toPx()
            val y = size.height - strokeWidth
            val start = Offset(0f, y)
            val end = Offset(size.width, y)
            onDrawBehind { drawLine(strokeColor, start, end, strokeWidth) }
        }.padding(start = 16.dp, end = 8.dp),
        Arrangement.spacedBy(8.dp), Alignment.CenterVertically
    ) {
        // Header
        Column(Modifier.weight(1F)) {
            Text(
                DemoData.userName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "${Res.string.online.text} • ${Res.string.last_seen_recently.text}",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge
            )
        }

        // Pinned Messages
        AtoriIconButton(Res.drawable.ic_pinned_msgs_24px, Res.string.pinned_messages.text)

        // Call
        AtoriIconButton(Res.drawable.ic_call_24px, Res.string.call.text)

        // Search Messages
        AtoriIconButton(Res.drawable.ic_search_msgs_24px, Res.string.search_messages.text)

        // Info
        AtoriIconButton(
            if (showInfoPanel) Res.drawable.ic_info_panel_invisible else Res.drawable.ic_info_panel_visible,
            if (showInfoPanel) Res.string.hide_info_panel.text else Res.string.show_info_panel.text
        ) {
            showInfoPanel = !showInfoPanel
        }
    }

    @Composable
    fun DemoChatPanelInfoPanel() =
        Box(Modifier.fillMaxHeight().width(280.dp).drawWithCache {
            val strokeWidth = 1.dp.toPx()
            val start = Offset(0f, 0f)
            val end = Offset(0f, size.height)
            onDrawBehind { drawLine(strokeColor, start, end, strokeWidth) }
        }) {
            DemoChatInfoPage()
        }

    Column(Modifier.fillMaxSize()) {
        DemoChatPanelTopBar()
        Row {
            Column(Modifier.weight(1F)) {
                Box(Modifier.weight(1F)) { DemoChatView() }
                DemoChatInputBar()
            }
            if (showInfoPanel) DemoChatPanelInfoPanel()
        }
    }
}

@Composable
fun MainPanel() = Box(
    Modifier.fillMaxSize().clip(RoundedCornerShape(topStart = 16.dp))
        .background(MaterialTheme.colorScheme.surfaceContainer)
) {
    if (UniversalStateStore.demoCurrentChat.value != -1) DemoChatPanel()
    else EmptyPage()
}