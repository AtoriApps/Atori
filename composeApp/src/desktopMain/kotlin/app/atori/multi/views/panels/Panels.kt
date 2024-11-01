package app.atori.multi.views.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.atori.multi.pages.ChatsPage
import app.atori.multi.stores.DesktopAppStateStore
import app.atori.multi.utils.ResUtils.vector
import atorimulti.composeapp.generated.resources.Res
import atorimulti.composeapp.generated.resources.ic_chats_24px
import atorimulti.composeapp.generated.resources.ic_chats_filled_24px
import org.jetbrains.compose.resources.DrawableResource

data class SidePanelTabItem(
    val icon: DrawableResource,
    val selectedIcon: DrawableResource,
    val name: String,
    val view: @Composable () -> Unit
)

@Composable
fun SidePanel() {
    var currentTab by remember { mutableStateOf(0) }

    val tabs = listOf(
        SidePanelTabItem(Res.drawable.ic_chats_24px, Res.drawable.ic_chats_filled_24px, "聊天") {
            ChatsPage()
        }
    )

    Row(Modifier.fillMaxHeight().padding(end = 8.dp)) {
        Column(
            Modifier.fillMaxHeight().width(64.dp).padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(Modifier.weight(1F), Arrangement.spacedBy(4.dp)) {
                tabs.forEachIndexed { index, tab ->
                    SidePanelTabButton(tab, index == currentTab)
                }
            }
            // TODO:控制条
            Column { }
        }
        // TODO:当前面板容器
        if (DesktopAppStateStore.sidePanelExpanded.value) {
            Box(
                Modifier.width(300.dp).clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                tabs[currentTab].view()
            }
        }
    }
}

@Composable
fun SidePanelTabButton(sidePanelTabItem: SidePanelTabItem, isCurrent: Boolean) {
    IconButton(
        onClick = {},
        Modifier.size(48.dp),
        true,
        if (isCurrent) IconButtonDefaults.filledTonalIconButtonColors() else IconButtonDefaults.iconButtonColors()
    ) {
        Icon((if (isCurrent) sidePanelTabItem.selectedIcon else sidePanelTabItem.icon).vector, sidePanelTabItem.name)
    }
}

@Composable
fun MainPanel() {
    Column(
        Modifier.fillMaxSize().clip(RoundedCornerShape(topStart = 16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) { }
}