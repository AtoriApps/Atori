package app.atori.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.atori.components.MsgItem
import app.atori.entities.DemoChatEntity
import app.atori.entities.DemoMessageBodyType
import app.atori.entities.DemoMessageBodyType.*
import app.atori.entities.DemoMessageEntity
import app.atori.entities.DemoMessageReactionEntity
import app.atori.entities.DemoMessageType
import app.atori.filledButtonColors
import app.atori.misc.DemoData
import app.atori.utils.ComposeUtils.paddingForSystemBars
import app.atori.utils.ResUtils.imgBmp
import app.atori.utils.ResUtils.text
import app.atori.utils.ResUtils.vector
import app.atori.utils.TimestampUtils.timeStr
import app.atori.utils.TimestampUtils.timestamp
import app.atori.viewModels.XmppViewModel
import app.atori.resources.*
import app.atori.resources.Res
import app.atori.resources.app_name
import app.atori.resources.ic_atori_logo_24px
import app.atori.resources.ic_chat_24px
import app.atori.stores.UniversalStateStore.demoCurrentChat
import app.atori.utils.ComposeUtils
import app.atori.utils.ComposeUtils.maybeHover
import app.atori.components.IconChip
import app.atori.components.IconStringChip
import org.jetbrains.compose.resources.DrawableResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DemoChatPage() {
    @Composable
    fun TheLine(modifier: Modifier) {
        val lineColor = MaterialTheme.colorScheme.outlineVariant

        Spacer(modifier.drawWithCache {
            val start = Offset(0f, size.height / 2)
            val end = Offset(size.width, size.height / 2)
            val lineWidth = 1.dp.toPx()
            onDrawBehind { drawLine(lineColor, start, end, lineWidth) }
        })
    }

    @Composable
    fun ReplyBlock(replyMsg: String) = Row(
        Modifier.fillMaxWidth().clickable(onClick = {})
            .padding(16.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Res.drawable.illu_reply_arrow.vector,
            Res.string.reply_arrow.text,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "@${DemoData.userName}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                replyMsg,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Composable
    fun TimeLineBlock(timestamp: Long) = Row(
        Modifier.fillMaxWidth().clickable(onClick = {}).padding(16.dp, 12.dp),
        Arrangement.spacedBy(8.dp),
        Alignment.CenterVertically
    ) {
        val lineModifier = Modifier.weight(1F)

        TheLine(lineModifier)
        Text(
            timestamp.timeStr("yyyy/MM/dd HH:mm"),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        TheLine(lineModifier)
    }

    @Composable
    fun ReadToHereBlock() = Row(
        Modifier.fillMaxWidth().clickable(onClick = {}).padding(16.dp, 12.dp),
        Arrangement.spacedBy(8.dp),
        Alignment.CenterVertically
    ) {
        Icon(
            Res.drawable.illu_read_to_here.vector,
            Res.string.read_to_here.text,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            Res.string.read_to_here.text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        TheLine(Modifier.weight(1F))
    }

    @Composable
    fun MessageBody(
        messageData: Any,
        messageType: DemoMessageBodyType,
        reactions: List<DemoMessageReactionEntity>
    ) {
        when (messageType) {
            TEXT -> Text(
                messageData as String,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            IMAGE -> Image(
                (messageData as DrawableResource).imgBmp,
                DemoData.userName,
                Modifier.size(400.dp, 280.dp).clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            NONE -> IllegalStateException("Message type is NONE.")
        }
        if (reactions.isNotEmpty()) LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reactions) { reaction ->
                IconStringChip(reaction.useClicked) {
                    Text(reaction.emoji)
                    Text(reaction.count.toString())
                }
            }
            item {
                IconChip(Res.drawable.ic_add_reaction_20px.vector, Res.string.add_reaction.text)
            }
        }
    }

    @Composable
    fun FirstMessageBlock(
        timestamp: Long, messageData: Any, messageType: DemoMessageBodyType,
        reactions: List<DemoMessageReactionEntity>
    ) = Row(
        Modifier.fillMaxWidth().clickable(onClick = {}).padding(16.dp, 4.dp),
        Arrangement.spacedBy(8.dp),
        Alignment.Top
    ) {
        Box(Modifier.width(64.dp), Alignment.CenterStart) {
            Image(
                DemoData.userAvatar, DemoData.userName,
                Modifier.size(40.dp).clip(CircleShape)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    DemoData.userName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    timestamp.timeStr("yyyy/MM/dd HH:mm"),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            MessageBody(messageData, messageType, reactions)
        }
    }

    @Composable
    fun HeadlessBlock(
        timestamp: Long, messageData: Any, messageType: DemoMessageBodyType,
        reactions: List<DemoMessageReactionEntity>
    ) = Row(
        Modifier.fillMaxWidth().clickable(onClick = {}).padding(16.dp, 4.dp),
        Arrangement.spacedBy(8.dp),
        Alignment.Top
    ) {
        var isHovered by remember { mutableStateOf(false) }

        Box(
            Modifier.size(64.dp, 24.dp).maybeHover(object : ComposeUtils.HoverHandler() {
                override fun onEnter(): Boolean {
                    isHovered = true
                    return false
                }

                override fun onExit(): Boolean {
                    isHovered = false
                    return false
                }
            })
            /*.pointerMoveFilter( // TODO: 这是个待移除的Api
                onEnter = {
                    isHovered = true
                    false
                },
                onExit = {
                    isHovered = false
                    false
                }
            )*/,
            Alignment.CenterStart
        ) {
            if (isHovered) Text(
                timestamp.timeStr("HH:mm"),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            MessageBody(messageData, messageType, reactions)
        }
    }

    val demoMessages = listOf(
        DemoMessageEntity(
            "度尽劫波兄弟在\n相逢一笑泯恩仇",
            "2024-11-11 11:45:14".timestamp,
            DemoMessageType.FIRST_MESSAGE, DemoMessageBodyType.TEXT
        ),
        DemoMessageEntity(
            Res.drawable.img_avatar_demo,
            "2024-11-11 11:45:14".timestamp,
            DemoMessageType.HEADLESS, DemoMessageBodyType.IMAGE,
            listOf(
                DemoMessageReactionEntity("😄", 2, true),
                DemoMessageReactionEntity("😅"),
            )
        ),
        DemoMessageEntity(
            null,
            "1919-11-11 11:45:14".timestamp,
            DemoMessageType.TIME_LINE
        ),
        DemoMessageEntity(
            "所以我说度尽劫波兄弟在，相逢一笑泯恩仇",
            "2024-11-11 11:45:14".timestamp,
            DemoMessageType.REPLY
        ),
        DemoMessageEntity(
            "度尽劫波兄弟在",
            "1919-08-10 11:45:14".timestamp,
            DemoMessageType.FIRST_MESSAGE, DemoMessageBodyType.TEXT
        ),
        DemoMessageEntity(
            null,
            "2024-08-10 11:45:14".timestamp,
            DemoMessageType.READ_TO_HERE
        ),
    )

    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)) {
        items(demoMessages) { msg ->
            when (msg.messageType) {
                DemoMessageType.FIRST_MESSAGE -> FirstMessageBlock(
                    msg.timestamp, msg.messageBodyData!!, msg.messageBodyType, msg.reactions
                )

                DemoMessageType.HEADLESS -> HeadlessBlock(
                    msg.timestamp, msg.messageBodyData!!, msg.messageBodyType, msg.reactions
                )

                DemoMessageType.TIME_LINE -> TimeLineBlock(msg.timestamp)
                DemoMessageType.REPLY -> ReplyBlock(msg.messageBodyData as String)
                DemoMessageType.READ_TO_HERE -> ReadToHereBlock()
            }
        }
    }
}

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

@Composable
fun ChatsPage() {
    @Composable
    fun ChatsPageChatItem(
        demoChatEntity: DemoChatEntity,
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
            DemoChatEntity(
                Res.drawable.img_avatar_demo,
                "Y. Lau",
                "宝宝❤\n永远爱你",
                "2024-11-01 11:45:14".timestamp,
                5,
                true, false, 2
            ),
            DemoChatEntity(
                Res.drawable.img_avatar_demo,
                "Earzu Chan",
                "还可以",
                "2024-11-01 11:45:14".timestamp,
                10,
                false, false, 0
            ),
            DemoChatEntity(
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

// All disposed below

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewPage(xmppViewModel: XmppViewModel) {
    val isLoggedIn by xmppViewModel.isLoggedIn.collectAsState(initial = false)
    val messages by xmppViewModel.messages.collectAsState(initial = emptyList())

    val listState = rememberLazyListState()
    val expandedFab = remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    Scaffold(
        Modifier,
        {
            TopAppBar(
                {
                    Text(
                        Res.string.app_name.text,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                Modifier,
                {
                    Box(Modifier.size(48.dp), Alignment.Center) {
                        Icon(
                            Res.drawable.ic_atori_logo_24px.vector,
                            Res.string.app_name.text, Modifier,
                            MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
            )
        },
        floatingActionButton = {
            Box(Modifier.padding(bottom = 8.dp, end = 8.dp)) {
                ExtendedFloatingActionButton(
                    { Text(if (isLoggedIn) Res.string.click_2_send.text else Res.string.connecting.text) },
                    {
                        Icon(
                            Res.drawable.ic_chat_24px.vector,
                            Res.string.click_2_send.text, Modifier
                        )
                    }, {
                        xmppViewModel.sendMessage("atr@conversations.im", "Hello, world!")
                    }, Modifier, expandedFab.value
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        // 可滑动的列表
        Box(Modifier.padding(innerPadding)) {
            LazyColumn(
                Modifier, listState, PaddingValues(8.dp, 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(messages.size) {
                    MsgItem(
                        messages[it].fromUser,
                        "${messages[it].messageBody}\n${messages[it].timestamp.timeStr}"
                    )
                }
            }
        }
    }
}

@Composable
fun LoginPage(xmppViewModel: XmppViewModel) {
    var step by remember { mutableStateOf(0) }
    val jid = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }
    val logining = remember { mutableStateOf(false) }

    Surface(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize().paddingForSystemBars().padding(24.dp),
            Arrangement.spacedBy(24.dp)
        ) {
            Image(
                Res.drawable.ic_gugled_atori_logo_48px.vector,
                Res.string.app_name.text,
                Modifier.size(48.dp)
            )
            Box(Modifier.weight(1F), Alignment.TopCenter) {
                when (step) {
                    0 -> LoginPartOfEnterUrJid(jid)
                    1 -> LoginPartOfEnterUrPassword(jid, password, showPassword) { step-- }
                }
            }
            Row {
                TextButton({},
                    Modifier,
                    false,
                    content = { Text(if (step == 0) "创建账号" else "忘记了密码") })
                Spacer(Modifier.weight(1F))
                FilledTonalButton(
                    {
                        when (step) {
                            0 -> if (jid.value.isNotEmpty()) step++
                            1 -> if (password.value.isNotEmpty()) {
                                logining.value = true
                                xmppViewModel.login(jid.value, password.value)
                            }
                        }
                    },
                    enabled = !logining.value,
                    colors = filledButtonColors,
                    content = { Text(if (logining.value) "正在登录" else "下一步") })
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginPartOfEnterUrJid(jid: MutableState<String>) {
    Column(Modifier.fillMaxSize(), Arrangement.spacedBy(24.dp)) {
        Column(Modifier.fillMaxWidth(), Arrangement.spacedBy(16.dp)) {
            Text(
                "登录",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                "登录到 Suchat.org",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Column(Modifier.fillMaxWidth(), Arrangement.spacedBy(44.dp)) {
            Column(Modifier.fillMaxWidth().padding(top = 10.dp), Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    jid.value,
                    { jid.value = it },
                    Modifier.fillMaxWidth(),
                    label = { Text("用户名") })
                FlowRow {
                    Text(
                        "忘记了用户名？",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "好似喵😆",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            FlowRow {
                Text(
                    "不是您自己的设备？",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "你爱登录不登录😎",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun LoginPartOfEnterUrPassword(
    jid: MutableState<String>,
    password: MutableState<String>,
    showPassword: MutableState<Boolean>,
    clickToBack: () -> Unit
) {
    Column(Modifier.fillMaxSize(), Arrangement.spacedBy(24.dp)) {
        Column(Modifier.fillMaxWidth(), Arrangement.spacedBy(20.dp)) {
            Text(
                jid.value.replaceFirstChar {
                    it.uppercase()
                },
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge
            )
            InputChip(false, clickToBack, {
                Text(jid.value)
            }, shape = MaterialTheme.shapes.extraLarge, avatar = {
                Image(
                    Res.drawable.ic_atori_icon.vector,
                    jid.value,
                    Modifier.clip(CircleShape).size(InputChipDefaults.AvatarSize)
                )
            }, trailingIcon = {
                Icon(
                    Res.drawable.ic_close_18px.vector,
                    jid.value,
                    Modifier.size(18.dp)
                )
            })
        }
        Column(
            Modifier.fillMaxWidth().padding(top = 22.dp),
            Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                password.value,
                { password.value = it },
                Modifier.fillMaxWidth(),
                label = { Text("输入您的密码") },
                visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Row(
                Modifier.fillMaxWidth()
                    .clickable { showPassword.value = !showPassword.value },
                Arrangement.spacedBy(16.dp),
                Alignment.CenterVertically
            ) {
                Checkbox(showPassword.value, { showPassword.value = it }, Modifier.size(24.dp))
                Text(
                    "显示密码",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}