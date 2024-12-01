package app.atori.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import app.atori.misc.DemoData
import app.atori.resources.Res
import app.atori.resources.call
import app.atori.resources.hide_info_panel
import app.atori.resources.ic_call_24px
import app.atori.resources.ic_info_panel_invisible
import app.atori.resources.ic_info_panel_visible
import app.atori.resources.ic_pinned_msgs_24px
import app.atori.resources.ic_search_msgs_24px
import app.atori.resources.last_seen_recently
import app.atori.resources.online
import app.atori.resources.pinned_messages
import app.atori.resources.search_messages
import app.atori.resources.show_info_panel
import app.atori.ui.components.AtoriIconButton
import app.atori.ui.views.DemoChatPageInputBar
import app.atori.ui.views.DemoChatView
import app.atori.ui.views.dialogs.DialogBase
import app.atori.utils.ResUtils.text

@Composable
fun DemoChatPage() {
    val strokeColor = MaterialTheme.colorScheme.outlineVariant
    var showInfoPanel by remember { mutableStateOf(false) }
    var showCallDialog by remember { mutableStateOf(false) }
    var showCallDialog2 by remember { mutableStateOf(false) }

    if (showCallDialog) DialogBase({ showCallDialog = false }) {
        Box(Modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min)) {
            DemoVoiceCallPage(true) {
                showCallDialog = false
            }
        }
    } else if (showCallDialog2) DialogBase({ showCallDialog2 = false }) {
        Box(Modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min)) { // TODO: 图片还是会撑满高度
            DemoVideoCallPage(true) {
                showCallDialog2 = false
            }
        }
    }

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
        AtoriIconButton(Res.drawable.ic_pinned_msgs_24px, Res.string.pinned_messages.text) {
            showCallDialog2 = true
        }

        // Call
        AtoriIconButton(Res.drawable.ic_call_24px, Res.string.call.text) {
            showCallDialog = true
        }

        // Search Messages
        AtoriIconButton(Res.drawable.ic_search_msgs_24px, Res.string.search_messages.text)

        // Info
        AtoriIconButton(
            if (showInfoPanel) Res.drawable.ic_info_panel_visible else Res.drawable.ic_info_panel_invisible,
            if (showInfoPanel) Res.string.hide_info_panel.text else Res.string.show_info_panel.text
        ) {
            showInfoPanel = !showInfoPanel
        }
    }

    @Composable
    fun DemoChatPanelDetailsPanel() =
        Box(Modifier.fillMaxHeight().width(280.dp).drawWithCache {
            val strokeWidth = 1.dp.toPx()
            val start = Offset(0f, 0f)
            val end = Offset(0f, size.height)
            onDrawBehind { drawLine(strokeColor, start, end, strokeWidth) }
        }) {
            DemoChatDetailsPage()
        }

    Column(Modifier.fillMaxSize()) {
        DemoChatPanelTopBar()
        Row {
            Column(Modifier.weight(1F)) {
                Box(Modifier.weight(1F)) { DemoChatView() }
                DemoChatPageInputBar()
            }
            if (showInfoPanel) DemoChatPanelDetailsPanel()
        }
    }
}