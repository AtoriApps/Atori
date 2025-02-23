package app.atori.ui.screens

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.atori.data.states.DemoState
import app.atori.misc.DemoConstants
import app.atori.resources.*
import app.atori.ui.components.AtoriIconButton
import app.atori.ui.components.AtoriMenuItem
import app.atori.ui.components.PrefabAtoriLogoIcon
import app.atori.ui.components.SimpleTextField
import app.atori.ui.pages.*
import app.atori.ui.views.DemoChatView
import app.atori.ui.views.DemoScreensTopBar
import app.atori.ui.views.dialogs.AboutDialog
import app.atori.ui.views.dialogs.DialogBase
import app.atori.ui.views.dialogs.OneAtoriDialog
import app.atori.utils.MultiplatformLogUtils
import app.atori.utils.ResUtils.imgBmp
import app.atori.utils.ResUtils.text
import app.atori.utils.ResUtils.vector
import org.koin.compose.koinInject

// TODO：下面这个导航脑瘫，会导致进到新Chat界面，再返回却不是回到主界面
@Composable
fun DemoVoiceCallScreen(rootNaviController: NavController, fromDetail: Boolean) =
    DemoVoiceCallPage {
        /*if (fromDetail) rootNaviController.popBackStack() else rootNaviController.navigate("chat") {
            popUpTo("video_call") { // 可选：从返回栈中移除 VideoCallScreen
                inclusive = true
            }
            launchSingleTop = true // 可选：避免创建多个 ChatScreen 实例
        }*/
        rootNaviController.popBackStack()
    }

@Composable
fun DemoVideoCallScreen(rootNaviController: NavController, fromDetail: Boolean) =
    DemoVideoCallPage {
        /*if (fromDetail) rootNaviController.popBackStack() else rootNaviController.navigate("chat") {
            popUpTo("video_call") { // 可选：从返回栈中移除 VideoCallScreen
                inclusive = true
            }
            launchSingleTop = true // 可选：避免创建多个 ChatScreen 实例
        }*/
        rootNaviController.popBackStack()
    }

@Composable
fun DemoIncomingCallScreen(rootNaviController: NavController) =
    DemoIncomingCallPage(false, true, {
        rootNaviController.popBackStack()
    }) {
        rootNaviController.navigate("voice_call/false") {
            popUpTo("incoming_call") { // 接受：弹出当前来电测试界面
                inclusive = true // 包含来电测试界面自身
            }
        }
    }

@Composable
fun DemoMainScreen() {
    val TAG = "DemoMainScreen"

    // 创建 NavController
    val tabNaviController = rememberNavController()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar() {
        var showOneAtori by remember { mutableStateOf(false) }

        var showAbout by remember { mutableStateOf(false) }

        if (showOneAtori)
            DialogBase({ showOneAtori = false }) {
                OneAtoriDialog { showAbout = true }
            }

        if (showAbout)
            DialogBase({ showAbout = false }) {
                AboutDialog()
            }

        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                .height(64.dp)
                .padding(horizontal = 8.dp)
        ) {
            // 左侧内容
            Row(Modifier.align(Alignment.CenterStart)) {
                Box(Modifier.size(48.dp), Alignment.Center) {
                    PrefabAtoriLogoIcon()
                }
            }

            // 中间标题
            Text(
                "Atori",
                Modifier.align(Alignment.Center),
                MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )

            // 右侧内容
            Row(Modifier.align(Alignment.CenterEnd)) {
                AtoriIconButton(Res.drawable.ic_search_24px, "Search", 48) {}

                AtoriIconButton({
                    showOneAtori = true
                }, 48) {
                    Image(
                        Res.drawable.img_avatar_demo.imgBmp,
                        "One Atori",
                        Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }

    @Composable
    fun BottomBar() = NavigationBar {

        // 通过 currentBackStackEntryAsState 监听当前页面，以便设置选中状态
        val navBackStackEntry by tabNaviController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        DemoConstants.navTabItems.forEach { tab ->
            NavigationBarItem(
                icon = {
                    Icon(
                        (if (currentRoute == tab.route) tab.selectedIcon else tab.icon).vector,
                        tab.name
                    )
                },
                label = { Text(tab.name) },
                selected = currentRoute == tab.route,
                onClick = {
                    if (currentRoute != tab.route) {
                        tabNaviController.navigate(tab.route) {
                            // 优化 Tab 切换体验，避免在同一个 Tab 内重复创建实例
                            popUpTo(tabNaviController.graph.findStartDestination().id) {
                                saveState = true // 保存前一个 Tab 的状态
                            }

                            launchSingleTop = true // 避免创建多个相同的实例
                            restoreState = true // 恢复前一个 Tab 的状态
                        }
                    }
                },
                alwaysShowLabel = false
            )
        }
    }

    val activity = LocalActivity.current

    // 并不能拦截导航
    BackHandler {
        /* 如果在主界面，则退出应用
         这里您可以根据实际情况选择退出应用的方式，例如：
         1. 直接退出应用 (不推荐，用户体验不好)
            exitProcess(0) // 需要 import kotlin.system.exitProcess
         2. 将应用移动到后台 (推荐)
            moveTaskToBack() // 需要在 Activity 中实现，这里只是示意
         3. 显示一个退出确认对话框 (更友好的方式)
            ... 显示对话框，用户确认后退出 ...*/

        MultiplatformLogUtils.d(TAG, "用户在主界面按了返回键 - 退出应用 (这里需要您实现具体的退出逻辑)")

        // 示例：将应用移动到后台
        activity?.moveTaskToBack(true)

        //  或者什么都不做，阻止默认返回行为
    }

    Scaffold(topBar = { TopBar() }, bottomBar = { BottomBar() })
    { padding ->
        // 使用 NavHost 定义导航图，起始页面设置为在线列表
        NavHost(
            navController = tabNaviController,
            startDestination = "chats",
            modifier = Modifier.padding(padding)
        ) {
            // 会话列表页面
            composable("chats") {
                DemoChatsPage()
            }

            // 联系人列表页面
            composable("contacts") {
                Text("Contacts")
            }

            // 发现页面
            composable("explore") {
                Text("Explore")
            }
        }
    }
}

@Composable
fun DemoChatScreen(rootNaviController: NavController) {
    val TAG = "DemoChatScreen"

    val demoState = koinInject<DemoState>()

    @Composable
    fun BottomBar() {
        var tempText by remember { mutableStateOf("") }

        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .windowInsetsPadding(BottomAppBarDefaults.windowInsets)
                .padding(12.dp), Arrangement.spacedBy(8.dp), Alignment.CenterVertically
        ) {

            Row(
                Modifier
                    .weight(1F)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .padding(4.dp), Arrangement.spacedBy(4.dp), Alignment.CenterVertically
            ) {
                AtoriIconButton(Res.drawable.ic_input_more_24px, Res.string.more.text)
                SimpleTextField(
                    tempText, { tempText = it },
                    Res.string.input_placeholder.text, Modifier.weight(1F)
                )
                AtoriIconButton(Res.drawable.ic_emoji_24px, Res.string.emoji.text)
            }
            AtoriIconButton(Res.drawable.ic_send_24px, Res.string.send.text, 48)
        }
    }

    var showMoreMenu by remember { mutableStateOf(false) }

    fun backToMain() {
        demoState.currentChat.value = -1
        rootNaviController.popBackStack()
    }

    BackHandler {
        MultiplatformLogUtils.d(TAG, "用户按了返回键")
        backToMain()
    }

    Scaffold(
        topBar = {
            DemoScreensTopBar(onClickBack = ::backToMain, onClickTitle = {
                rootNaviController.navigate("chat_details")
            }, onClickMore = {
                showMoreMenu = true
            }, showDropMenu = showMoreMenu, onDismissDropMenu = {
                showMoreMenu = false
            }, dropMenuContent = {
                AtoriMenuItem(Res.string.pinned_messages.text, Res.drawable.ic_pinned_msgs_24px)
                AtoriMenuItem(Res.string.call.text, Res.drawable.ic_call_24px) {
                    rootNaviController.navigate("voice_call/false")
                }
                AtoriMenuItem(Res.string.search_messages.text, Res.drawable.ic_search_msgs_24px)
            })
        },
        bottomBar = { BottomBar() })
    { padding ->
        Box(Modifier.padding(padding), Alignment.Center) {
            DemoChatView()
        }
    }
}

@Composable
fun DemoChatDetailsScreen(rootNaviController: NavController) {
    Scaffold(
        topBar = {
            DemoScreensTopBar(
                Res.string.chat_details.text, showSubtitle = false, showMoreButton = false
            ) { rootNaviController.popBackStack() }
        })
    { padding ->
        Box(Modifier.padding(padding), Alignment.Center) {
            DemoChatDetailsPage(true, {
                rootNaviController.navigate("video_call/true")
            }, {
                rootNaviController.navigate("voice_call/true")
            }) {
                rootNaviController.navigate("incoming_call")
            }
        }
    }
}