package app.atori.ui.pages

// import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.atori.misc.DemoData
import app.atori.models.DemoChatModel
import app.atori.resources.*
import app.atori.resources.Res
import app.atori.resources.ic_chat_24px
import app.atori.stores.DemoStateStore
import app.atori.ui.components.AtoriIconButton
import app.atori.ui.components.AtoriIconButtonStyles
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
fun DemoChatDetailsPage(
    showActions: Boolean = false,
    onClickVideoCall: () -> Unit = {},
    onClickVoiceCall: () -> Unit = {}
) = LazyColumn(
    Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    @Composable
    fun FakeStatusOwnedPreferenceItem(
        icon: DrawableResource,
        title: String,
        content: String,
        subtitle: String? = null
    ) = PreferenceItem(icon, title, subtitle) {
        Text(
            content, color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
    }

    // Status
    val esDp = 24.dp
    item {
        Column(
            Modifier.Companion.fillMaxWidth().padding(vertical = 16.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Image(
                DemoData.userAvatar, DemoData.userName,
                Modifier.padding(bottom = esDp).size(128.dp).clip(CircleShape)
            )
            Text(
                DemoData.userName, Modifier.padding(bottom = 8.dp),
                MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                "${Res.string.online.text} • ${Res.string.last_seen_recently.text}",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge
            )
            if (showActions) Row(
                Modifier.padding(top = esDp),
                Arrangement.spacedBy(esDp)
            ) {
                AtoriIconButton(
                    Res.drawable.ic_pinned_msgs_24px, Res.string.pinned_messages.text,
                    48, AtoriIconButtonStyles.Tonal, onClick = onClickVideoCall
                )
                AtoriIconButton(
                    Res.drawable.ic_chat_24px, Res.string.chat.text,
                    48, AtoriIconButtonStyles.Tonal
                )
                AtoriIconButton(
                    Res.drawable.ic_call_24px, Res.string.call.text,
                    48, AtoriIconButtonStyles.Tonal, onClick = onClickVoiceCall
                )
                AtoriIconButton(
                    Res.drawable.ic_search_msgs_24px, Res.string.search_messages.text,
                    48, AtoriIconButtonStyles.Tonal
                )
            }
        }
    }

    // Settings
    item {
        PreferenceGroup(Res.string.information.text) {
            PreferenceItem(
                Res.drawable.ic_id_platform_24px,
                Res.string.a_contact_from.text(DemoData.userPlatform),
                Res.string.you_re_using.text(DemoData.userJid)
            )
            PreferenceItem(
                Res.drawable.ic_id_card_24px, Res.string.tap_to_copy_other_party_s_account.text,
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
            var receiveStatusUpdate by remember { mutableStateOf(true) }
            var canNotify by remember { mutableStateOf(true) }
            SwitchPreferenceItem(
                Res.drawable.ic_share_status_24px,
                Res.string.share_status_update.text, null, shareStatusUpdate
            ) { shareStatusUpdate = it }
            SwitchPreferenceItem(
                Res.drawable.ic_receive_status_24px,
                Res.string.receive_other_party_status.text, null, receiveStatusUpdate
            ) { receiveStatusUpdate = it }
            FakeStatusOwnedPreferenceItem(
                Res.drawable.ic_user_permission_24px,
                Res.string.permission_manage.text, Res.string.only_chat.text
            )
            SwitchPreferenceItem(
                Res.drawable.ic_notification_24px,
                Res.string.notify.text, null, canNotify
            ) { canNotify = it }
            FakeStatusOwnedPreferenceItem(
                Res.drawable.ic_msg_encryption_24px,
                Res.string.message_encryption.text, Res.string.omemo.text,
                Res.string.tap_to_view_details.text
            )
            FakeStatusOwnedPreferenceItem(
                Res.drawable.ic_contacts_24px,
                Res.string.members.text, "3"
            )
            FakeStatusOwnedPreferenceItem(
                Res.drawable.ic_media_24px, Res.string.media.text,
                "18+", Res.string.photos_av_files.text
            )
            PreferenceItem(
                Res.drawable.ic_export_chat_history_24px, Res.string.export_chat_history.text,
                Res.string.backup_to_a_local_file.text
            )
            FakeStatusOwnedPreferenceItem(
                Res.drawable.ic_clear_chat_history_24px, Res.string.clear_chat_history.text,
                Res.string.pieces.text("1919"), Res.string.will_lost_them_forever.text
            )
        }
        PreferenceGroup(Res.string.contact.text) {
            var pinChat by remember { mutableStateOf(false) }
            PreferenceItem(
                Res.drawable.ic_edit_24px, Res.string.edit_info.text,
                Res.string.remarks_labels_contact_ways_descriptions.text
            )
            SwitchPreferenceItem(
                Res.drawable.ic_pin_24px, Res.string.pin_this_chat.text, null, pinChat
            ) { pinChat = it }
            PreferenceItem(Res.drawable.ic_report_24px, Res.string.report.text)
            PreferenceItem(
                Res.drawable.ic_delete_24px,
                Res.string.delete_contact.text, Res.string.ur_contact.text
            )
            PreferenceItem(
                Res.drawable.ic_add_person_24px,
                Res.string.add_contact.text, Res.string.not_ur_contact.text
            )
            PreferenceItem(Res.drawable.ic_leave_24px, Res.string.leave_group.text)
            PreferenceItem(Res.drawable.ic_block_24px, Res.string.block.text)
        }
    }
}

@Composable
fun DemoVoiceCallPage(
    isInDialog: Boolean = false,
    onClickClose: () -> Unit = {}
) = Column(
    Modifier.fillMaxSize()
        .background(if (isInDialog) MaterialTheme.colorScheme.surfaceContainerHigh else MaterialTheme.colorScheme.surface)
        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)) // FIXME: 是否应该抽离
        .padding(if (isInDialog) 8.dp else 0.dp).clip(RoundedCornerShape(20.dp))
)
{
    // 标题栏与时间
    Box(
        Modifier.fillMaxWidth()
            .background(if (isInDialog) MaterialTheme.colorScheme.surface else Color.Transparent)
            .padding(horizontal = 4.dp)
            .height(if (isInDialog) 48.dp else 64.dp)
    ) {
        Text(
            "11:45", Modifier.align(Alignment.Center), MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
        if (isInDialog) AtoriIconButton(
            Res.drawable.ic_close_24px, Res.string.close.text,
            modifier = Modifier.align(Alignment.CenterEnd), onClick = onClickClose
        )
    }

    // 头像与名称
    val tOrZero = (if (isInDialog) 20 else 0).dp
    val sOr24 = (if (isInDialog) 16 else 24).dp
    Column(
        Modifier.fillMaxWidth().weight(1F)
            .clip(RoundedCornerShape(bottomEnd = tOrZero, bottomStart = tOrZero))
            .background(if (isInDialog) MaterialTheme.colorScheme.surface else Color.Transparent)
            .padding(vertical = sOr24),
        if (isInDialog) Arrangement.Center else Arrangement.Top,
        Alignment.CenterHorizontally
    ) {
        Image(
            DemoData.userAvatar, DemoData.userName,
            Modifier.padding(bottom = sOr24).size(128.dp)
                .clip(CircleShape)
        )
        Text(
            DemoData.userName, Modifier.padding(bottom = 8.dp),
            MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineSmall
        )
        Text(
            DemoData.userJid,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
    }

    // 按钮面板
    val ebDp = 28.dp
    val sOrSE = (if (isInDialog) 16 else 48).dp
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(topStart = ebDp, topEnd = ebDp))
            .background(if (isInDialog) Color.Transparent else MaterialTheme.colorScheme.surfaceContainerHigh)
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom)) // FIXME: 是否应该抽离
            .padding(
                top = if (isInDialog) 16.dp else 32.dp,
                bottom = sOrSE
            ), Arrangement.spacedBy(sOrSE), Alignment.CenterHorizontally
    ) {
        @Composable
        fun TitledActionIconButton(
            icon: DrawableResource,
            title: String,
            isOn: Boolean = false,
            onClick: () -> Unit
        ) = Column(Modifier, Arrangement.spacedBy(12.dp), Alignment.CenterHorizontally) {
            AtoriIconButton(
                icon, title, 48,
                if (isOn) AtoriIconButtonStyles.Filled else AtoriIconButtonStyles.SpecialNotActivated,
                onClick = onClick
            )
            Text(
                title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        var micOff by remember { mutableStateOf(false) }
        var speakerOn by remember { mutableStateOf(false) }
        var recordOn by remember { mutableStateOf(false) }

        Row(Modifier.fillMaxWidth().padding(horizontal = 32.dp), Arrangement.SpaceBetween) {
            TitledActionIconButton(
                Res.drawable.ic_mic_off_24px, Res.string.mute.text, micOff
            ) { micOff = !micOff }
            TitledActionIconButton(
                Res.drawable.ic_speacker_24px, Res.string.speaker.text, speakerOn
            ) { speakerOn = !speakerOn }
            TitledActionIconButton(
                Res.drawable.ic_record_24px, Res.string.record.text, recordOn
            ) { recordOn = !recordOn }
        }

        AtoriIconButton(
            onClickClose, 64, AtoriIconButtonStyles.SpecialError
        ) {
            Icon(
                Res.drawable.ic_end_call_24px.vector, Res.string.end_call.text, Modifier.size(36.dp)
            )
        }
    }
}

@Composable
fun DemoVideoCallPage(
    isInDialog: Boolean = false,
    onClickClose: () -> Unit = {}
) = Column(
    Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainerHigh)
        .padding(if (isInDialog) 8.dp else 0.dp)
        .clip(RoundedCornerShape((if (isInDialog) 20 else 0).dp))
)
{
    // 标题栏与时间
    Box(
        Modifier.clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(Color.Black).weight(1F)

    ) {
        // FIXME: 弹窗情况下图片老想撑满
        Image(
            Res.drawable.img_video_call_demo.imgBmp,
            Res.string.call.text,
            Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        Box(
            Modifier.align(Alignment.TopCenter)
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)) // FIXME: 是否应该抽离.fillMaxWidth()
                .padding(horizontal = 4.dp)
                .height(if (isInDialog) 48.dp else 64.dp)
        ) {
            Text(
                "11:45", Modifier.align(Alignment.Center), // 整一个文本都被.shadow(2.dp),
                MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge.merge(
                    shadow = Shadow(blurRadius = 4F)
                )
            )
            /*if (isInDialog) AtoriIconButton( // 怕和视频通话的自己的画面重叠
                Res.drawable.ic_close_24px, Res.string.close.text,
                modifier = Modifier.align(Alignment.CenterEnd), onClick = onClickClose
            )*/
        }
    }

    // 按钮面板
    val sOrSE = (if (isInDialog) 16 else 48).dp
    Column(
        Modifier.fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom)) // FIXME: 是否应该抽离
            .padding(
                top = if (isInDialog) 16.dp else 32.dp,
                bottom = sOrSE
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        @Composable
        fun TitledActionIconButton(
            icon: DrawableResource,
            title: String,
            isOn: Boolean = false,
            onClick: () -> Unit
        ) = Column(Modifier, Arrangement.spacedBy(12.dp), Alignment.CenterHorizontally) {
            AtoriIconButton(
                icon, title, 48,
                if (isOn) AtoriIconButtonStyles.Filled else AtoriIconButtonStyles.SpecialNotActivated,
                onClick = onClick
            )
            Text(
                title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            DemoData.userName, Modifier.padding(bottom = 8.dp),
            MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineSmall
        )
        Text(
            DemoData.userJid, Modifier.padding(bottom = (if (isInDialog) 16 else 24).dp),
            MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )

        var micOff by remember { mutableStateOf(false) }
        var speakerOn by remember { mutableStateOf(false) }
        var cameraOff by remember { mutableStateOf(false) }
        var frontCamera by remember { mutableStateOf(false) }

        Row(Modifier.fillMaxWidth().padding(horizontal = 32.dp), Arrangement.SpaceBetween) {
            TitledActionIconButton(
                Res.drawable.ic_mic_off_24px, Res.string.mute.text, micOff
            ) { micOff = !micOff }
            TitledActionIconButton(
                Res.drawable.ic_speacker_24px, Res.string.speaker.text, speakerOn
            ) { speakerOn = !speakerOn }
            TitledActionIconButton(
                Res.drawable.ic_no_camera_24px, Res.string.no_video.text, cameraOff
            ) { cameraOff = !cameraOff }
            TitledActionIconButton(
                Res.drawable.ic_switch_camera_24px, Res.string.front_camera.text, frontCamera
            ) { frontCamera = !frontCamera }
        }

        AtoriIconButton(
            onClickClose, 64, AtoriIconButtonStyles.SpecialError, Modifier.padding(top = sOrSE)
        ) {
            Icon(
                Res.drawable.ic_end_call_24px.vector, Res.string.end_call.text, Modifier.size(36.dp)
            )
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
                ChatsPageChatItem(chat, index == DemoStateStore.currentChat.value) {
                    DemoStateStore.currentChat.value = index
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