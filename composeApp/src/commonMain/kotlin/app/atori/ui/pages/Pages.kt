package app.atori.ui.pages

// import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.atori.misc.DemoData
import app.atori.models.DemoChatModel
import app.atori.resources.*
import app.atori.resources.Res
import app.atori.resources.ic_chat_24px
import app.atori.stores.UniversalStateStore.demoCurrentChat
import app.atori.ui.components.PreferenceGroup
import app.atori.ui.components.PreferenceItem
import app.atori.ui.components.SwitchPreferenceItem
import app.atori.utils.ResUtils.imgBmp
import app.atori.utils.ResUtils.text
import app.atori.utils.ResUtils.vector
import app.atori.utils.TimestampUtils.timeStr
import app.atori.utils.TimestampUtils.timestamp
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun DemoChatInfoPage() = LazyColumn(
    Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    // Status
    item {
        Row(
            Modifier.padding(horizontal = 24.dp), Arrangement.spacedBy(16.dp),
            Alignment.CenterVertically
        ) {
            Image(
                DemoData.userAvatar, DemoData.userName,
                Modifier.size(80.dp).clip(CircleShape)
            )
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
        }
    }

    // Settings
    item {
        PreferenceGroup(Res.string.information.text) {
            PreferenceItem(
                Res.drawable.ic_id_card_24px, Res.string.tap_to_copy_jid.text,
                DemoData.userJid
            )
            PreferenceItem(Res.drawable.ic_qr_code_24px, Res.string.generate_qr_code.text)
            PreferenceItem(
                Res.drawable.ic_share_24px, Res.string.share.text,
                Res.string.xmpp_or_http_link.text
            )
        }
        PreferenceGroup(Res.string.chat.text) {
            var shareStatusUpdate by remember { mutableStateOf(true) }
            SwitchPreferenceItem(
                Res.drawable.ic_share_status_24px,
                Res.string.share_status_update.text, null, shareStatusUpdate
            ) { shareStatusUpdate = it }
        }
    }
}

@Composable
fun DemoChatsPage() {
    @Composable
    fun ChatsPageChatItem(
        demoChatEntity: DemoChatModel,
        isCurrent: Boolean,
        onClick: () -> Unit
    ) {
        @Composable
        fun ChatsPageChatItemStateIcon(
            icon: DrawableResource,
            name: String,
            isCurrent: Boolean
        ) {
            Icon(
                icon.vector,
                name,
                Modifier.size(24.dp),
                if (isCurrent) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            Modifier.fillMaxWidth().height(IntrinsicSize.Min)
                .clip(MaterialTheme.shapes.large)
                .background(if (isCurrent) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                .clickable(onClick = onClick).padding(12.dp),
            Arrangement.spacedBy(16.dp)
        ) {
            Image(
                demoChatEntity.avatar.imgBmp,
                demoChatEntity.name,
                Modifier.size(40.dp).clip(CircleShape)
            )
            Column(Modifier.weight(1F)) {
                Text(
                    demoChatEntity.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isCurrent) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    demoChatEntity.lastMessage,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (isCurrent) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Column(Modifier.weight(1F), Arrangement.spacedBy(4.dp), Alignment.End) {
                    Text(
                        demoChatEntity.timestamp.timeStr("HH:mm"),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isCurrent) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (demoChatEntity.unreadCount > 0) Badge { Text(demoChatEntity.unreadCount.toString()) }
                }

                Row {
                    if (demoChatEntity.pinned) {
                        ChatsPageChatItemStateIcon(
                            Res.drawable.ic_pinned_20px,
                            "Pinned",
                            isCurrent
                        )

                    }
                    if (demoChatEntity.muted) {
                        ChatsPageChatItemStateIcon(
                            Res.drawable.ic_muted_20px,
                            "Muted",
                            isCurrent
                        )
                    }
                    when (demoChatEntity.sendState) {
                        1 -> ChatsPageChatItemStateIcon(
                            Res.drawable.ic_tick_20px,
                            "Sent",
                            isCurrent
                        )

                        2 -> ChatsPageChatItemStateIcon(
                            Res.drawable.ic_two_ticks_20px,
                            "Received",
                            isCurrent
                        )
                    }
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        val demoConversations = listOf(
            DemoChatModel(
                Res.drawable.img_avatar_demo,
                "Y. Lau",
                "宝宝❤\n永远爱你",
                "2024-11-01 11:45:14".timestamp,
                5,
                true, false, 2
            ),
            DemoChatModel(
                Res.drawable.img_avatar_demo,
                "Earzu Chan",
                "还可以",
                "2024-11-01 11:45:14".timestamp,
                10,
                false, false, 0
            ),
            DemoChatModel(
                Res.drawable.img_avatar_demo,
                "邹111X",
                "度尽劫波兄弟在",
                "2024-11-01 11:45:14".timestamp,
                0,
                false, true, 1
            )
        )

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            itemsIndexed(demoConversations) { index, chat ->
                ChatsPageChatItem(chat, index == demoCurrentChat.value) {
                    demoCurrentChat.value = index
                }
            }
        }

        Box(Modifier.fillMaxSize().padding(bottom = 16.dp, end = 16.dp), Alignment.BottomEnd) {
            ExtendedFloatingActionButton(
                { Text(Res.string.new_chat.text) },
                {
                    Icon(
                        Res.drawable.ic_chat_24px.vector,
                        Res.string.click_2_send.text, Modifier
                    )
                }, { }
            )
        }
    }
}

@Composable
fun EmptyPage() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Icon(
            Res.drawable.illu_empty_page.vector,
            Res.string.empty_page.text,
            tint = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}