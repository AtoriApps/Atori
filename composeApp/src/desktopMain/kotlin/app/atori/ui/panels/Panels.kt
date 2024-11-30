package app.atori.ui.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.atori.models.NavTabItem
import app.atori.ui.pages.EmptyPage
import app.atori.stores.DesktopAppStateStore
import app.atori.resources.*
import app.atori.resources.Res
import app.atori.stores.AppStateStore
import app.atori.stores.DemoStateStore
import app.atori.ui.components.AtoriIconButton
import app.atori.ui.components.AtoriIconButtonStyles
import app.atori.ui.pages.DemoChatPage
import app.atori.utils.ResUtils.text

@Composable
fun SidePanel() {
    @Composable
    fun ItemButton(
        sidePanelItem: NavTabItem,
        isCurrent: Boolean = false,
        onClick: () -> Unit = {}
    ) = AtoriIconButton(
        if (isCurrent) sidePanelItem.selectedIcon else sidePanelItem.icon,
        sidePanelItem.name,
        48,
        if (isCurrent) AtoriIconButtonStyles.Tonal else AtoriIconButtonStyles.Standard,
        onClick = onClick
    )

    Row(Modifier.fillMaxHeight()) {
        Column(
            Modifier.fillMaxHeight().width(64.dp).padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 顶排按钮
            Column(Modifier.weight(1F), Arrangement.spacedBy(4.dp)) {
                AppStateStore.navTabItems.forEachIndexed { index, tab ->
                    ItemButton(tab, index == AppStateStore.currentNavTab.value) {
                        AppStateStore.currentNavTab.value = index
                    }
                }
            }
            // 底排按钮
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                AtoriIconButton(
                    if (DesktopAppStateStore.sidePanelExpanded.value) Res.drawable.ic_panel_narrow_24px
                    else Res.drawable.ic_panel_24px, Res.string.expand_or_side_panel.text, 48
                ) {
                    DesktopAppStateStore.sidePanelExpanded.value =
                        !DesktopAppStateStore.sidePanelExpanded.value
                }
            }
        }

        // TODO:当前面板容器
        if (DesktopAppStateStore.sidePanelExpanded.value) {
            Box(
                Modifier.padding(end = 8.dp).width(300.dp).fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                Alignment.Center
            ) {
                AppStateStore.navTabItems[AppStateStore.currentNavTab.value].view()
            }
        }
    }
}

@Composable
fun MainPanel() = Box(
    Modifier.fillMaxSize().clip(RoundedCornerShape(topStart = 16.dp))
        .background(MaterialTheme.colorScheme.surfaceContainer)
) {
    if (DemoStateStore.currentChat.value != -1) DemoChatPage()
    else EmptyPage()
}