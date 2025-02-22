package app.atori.ui.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.atori.data.states.DemoState
import app.atori.misc.DemoConstants
import app.atori.ui.pages.EmptyPage
import app.atori.resources.*
import app.atori.resources.Res
import app.atori.ui.components.AtoriIconButton
import app.atori.ui.components.AtoriIconButtonStyles
import app.atori.ui.models.NavTabItem
import app.atori.ui.pages.DemoChatPage
import app.atori.ui.pages.DemoChatsPage
import app.atori.ui.viewmodels.SidePanelViewModel
import app.atori.utils.ResUtils.text
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SidePanel() {
    // 创建 NavController
    val tabNaviController = rememberNavController()

    val sidePanelViewModel = koinViewModel<SidePanelViewModel>()

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
                // 通过 currentBackStackEntryAsState 监听当前页面，以便设置选中状态
                val navBackStackEntry by tabNaviController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                DemoConstants.navTabItems.forEach { tab ->
                    ItemButton(tab, currentRoute == tab.route) {
                        if (currentRoute != tab.route) {
                            tabNaviController.navigate(tab.route) {
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
            // 底排按钮
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                AtoriIconButton(
                    if (sidePanelViewModel.sidePanelExpanded.value) Res.drawable.ic_panel_narrow_24px
                    else Res.drawable.ic_panel_24px, Res.string.expand_or_side_panel.text, 48
                ) {
                    sidePanelViewModel.sidePanelExpanded.value =
                        !sidePanelViewModel.sidePanelExpanded.value
                }
            }
        }

        // TODO:当前面板容器
        if (sidePanelViewModel.sidePanelExpanded.value) {
            NavHost(
                navController = tabNaviController,
                startDestination = "chats",
                modifier = Modifier.padding(end = 8.dp).width(300.dp).fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
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
}

// TODO：IMPL in NaVi
@Composable
fun MainPanel() = Box(
    Modifier.fillMaxSize().clip(RoundedCornerShape(topStart = 16.dp))
        .background(MaterialTheme.colorScheme.surfaceContainer)
) {
    val demoViewModel = koinInject<DemoState>()

    if (demoViewModel.currentChat.value != -1) DemoChatPage()
    else EmptyPage()
}