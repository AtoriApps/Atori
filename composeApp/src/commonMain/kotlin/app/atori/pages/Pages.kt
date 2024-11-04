package app.atori.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.atori.components.ElegantEditableLabel
import app.atori.components.MsgItem
import app.atori.entities.DemoChatEntity
import app.atori.filledButtonColors
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
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun ChatsPage() {
    @Composable
    fun ChatsPageChatItem(demoChatEntity: DemoChatEntity, isCurrent: Boolean, onClick: () -> Unit) {
        @Composable
        fun ChatsPageChatItemStateIcon(icon: DrawableResource, name: String, isCurrent: Boolean) {
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
            Image(demoChatEntity.avatar.imgBmp, demoChatEntity.name, Modifier.size(40.dp).clip(CircleShape))
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
                        ChatsPageChatItemStateIcon(Res.drawable.ic_pinned_20px, "Pinned", isCurrent)

                    }
                    if (demoChatEntity.muted) {
                        ChatsPageChatItemStateIcon(Res.drawable.ic_muted_20px, "Muted", isCurrent)
                    }
                    when (demoChatEntity.sendState) {
                        1 -> ChatsPageChatItemStateIcon(Res.drawable.ic_tick_20px, "Sent", isCurrent)
                        2 -> ChatsPageChatItemStateIcon(Res.drawable.ic_two_ticks_20px, "Received", isCurrent)
                    }
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            val demoConversations = listOf(
                DemoChatEntity(
                    Res.drawable.img_avatar_demo,
                    "Y. Law",
                    "宝宝❤\n我想一辈子和你走下去",
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

            var demoCurrentChat by remember { mutableStateOf(-1) }

            ElegantEditableLabel()
            LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(demoConversations) { index, chat ->
                    ChatsPageChatItem(chat, index == demoCurrentChat) { demoCurrentChat = index }
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
                { Text(Res.string.app_name.text, maxLines = 1, overflow = TextOverflow.Ellipsis) }, Modifier,
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
                    MsgItem(messages[it].fromUser, "${messages[it].messageBody}\n${messages[it].timestamp.timeStr}")
                }
            }
        }
    }
}

@Composable
fun ChatPage() {

}

@Composable
fun LoginPage(xmppViewModel: XmppViewModel) {
    var step by remember { mutableStateOf(0) }
    val jid = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }
    val logining = remember { mutableStateOf(false) }

    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().paddingForSystemBars().padding(24.dp), Arrangement.spacedBy(24.dp)) {
            Image(Res.drawable.ic_gugled_atori_logo_48px.vector, Res.string.app_name.text, Modifier.size(48.dp))
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
            Text(jid.value.replaceFirstChar {
                it.uppercase()
            }, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineLarge)
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