package app.atori.views.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.atori.entities.NavTabItem
import app.atori.standardIconButtonColors
import app.atori.stores.DesktopAppStateStore
import app.atori.utils.ResUtils.vector
import app.atori.resources.*
import app.atori.resources.Res
import app.atori.stores.UniversalStateStore

@Composable
fun SidePanel() {
    @Composable
    fun ItemButton(sidePanelItem: NavTabItem, isCurrent: Boolean = false, onClick: () -> Unit = {}) {
        Button(
            onClick,
            Modifier.size(48.dp),
            contentPadding = PaddingValues(0.dp),
            colors = if (isCurrent) ButtonDefaults.filledTonalButtonColors() else standardIconButtonColors
        ) {
            Icon((if (isCurrent) sidePanelItem.selectedIcon else sidePanelItem.icon).vector, sidePanelItem.name)
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
                    { DesktopAppStateStore.sidePanelExpanded.value = !DesktopAppStateStore.sidePanelExpanded.value },
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
                Modifier.padding(end = 8.dp).width(300.dp).fillMaxHeight().clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                UniversalStateStore.navTabItems[UniversalStateStore.currentNavTab.value].view()
            }
        }
    }
}

@Composable
fun MainPanel() {
    Column(
        Modifier.fillMaxSize().clip(RoundedCornerShape(topStart = 16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) { }
}