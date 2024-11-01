package app.atori.multi.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import app.atori.multi.components.ElegantEditableLabel
import app.atori.multi.components.MsgItem
import app.atori.multi.entities.DemoChatEntity
import app.atori.multi.filledButtonColors
import app.atori.multi.utils.ComposeUtils.paddingForSystemBars
import app.atori.multi.utils.ResUtils.imgBmp
import app.atori.multi.utils.ResUtils.text
import app.atori.multi.utils.ResUtils.vector
import app.atori.multi.utils.TimestampUtils.timeStr
import app.atori.multi.utils.TimestampUtils.timestamp
import app.atori.multi.viewModels.XmppViewModel
import atorimulti.composeapp.generated.resources.*
import atorimulti.composeapp.generated.resources.Res
import atorimulti.composeapp.generated.resources.app_name
import atorimulti.composeapp.generated.resources.ic_atori_logo_24px
import atorimulti.composeapp.generated.resources.ic_chat_24px

@Composable
fun ChatsPage() {
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        val demoConversations = listOf(
            DemoChatEntity(
                Res.drawable.img_avatar_demo,
                "Y. Law",
                "❤",
                "2024-11-01 11:45:14".timestamp,
                5,
                true, false
            ),
            DemoChatEntity(
                Res.drawable.img_avatar_demo,
                "Earzu Chan",
                "还可以",
                "2024-11-01 11:45:14".timestamp,
                10,
                false, false
            )
        )

        var demoCurrentChat by remember { mutableStateOf(-1) }

        ElegantEditableLabel()
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(2.dp)) {
            demoConversations.forEachIndexed { index, chat ->
                ChatsPageChatItem(chat, index == demoCurrentChat) { demoCurrentChat = index }
            }
        }
    }
}

@Composable
fun ChatsPageChatItem(demoChatEntity: DemoChatEntity, isCurrent: Boolean, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth()
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
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                demoChatEntity.lastMessage,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(/*Modifier.wrapContentHeight(),*/horizontalAlignment = Alignment.End) {
            Column(Modifier/*.weight(1F) FIXME: 这里不能用weight1*/, Arrangement.spacedBy(4.dp), Alignment.End) {
                Text(
                    demoChatEntity.timestamp.timeStr("HH:mm"),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (demoChatEntity.unreadCount > 0) Badge { Text(demoChatEntity.unreadCount.toString()) }
            }
            Row {
                // TODO: 聊天状态指示器
            }
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
            Text("登录", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineLarge)
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