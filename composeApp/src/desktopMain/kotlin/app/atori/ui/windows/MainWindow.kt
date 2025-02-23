package app.atori.ui.windows

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import app.atori.ui.AtoriTheme
import app.atori.utils.ResUtils.text
import app.atori.resources.Res
import app.atori.resources.app_name
import app.atori.resources.ic_atori_icon
import app.atori.resources.ic_atori_icon_dark
import app.atori.ui.panels.MainPanel
import app.atori.ui.panels.SidePanel
import app.atori.ui.viewmodels.MainWindowViewModel
import app.atori.ui.views.MainWindowTopBar
import app.atori.utils.ComposeUtils.only
import app.atori.utils.MultiplatformLogUtils
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

// 怎么确定只有单例
@Composable
fun MainWindow(appScope: ApplicationScope) {
    val windowState = rememberWindowState()

    Window(
        state = windowState,
        icon = if (isSystemInDarkTheme()) painterResource(Res.drawable.ic_atori_icon)
        else painterResource(Res.drawable.ic_atori_icon_dark),
        onCloseRequest = appScope::exitApplication, title = Res.string.app_name.text,
        undecorated = true, transparent = true
    ) {
        val mainWindowViewModel = koinViewModel<MainWindowViewModel>()
        mainWindowViewModel.setMainWindowInstance(appScope, this@Window, windowState)

        AtoriTheme {
            val rcs17 = RoundedCornerShape(17.dp)

            Surface(
                Modifier.fillMaxSize()
                    // 窗口基本样式，另外发现这 Surface 和 IconButton 的主题色有关
                    .only(!mainWindowViewModel.isMaximized) { clip(rcs17) }
                    .background(MaterialTheme.colorScheme.surface)
                    .only(!mainWindowViewModel.isMaximized) {
                        border(1.dp, MaterialTheme.colorScheme.outlineVariant, rcs17).padding(1.dp)
                    }
                // TODO: 以后限制窗口大小
            ) {
                // 窗口主骨架
                Column(
                    Modifier.fillMaxSize()
                ) {
                    MainWindowTopBar()
                    Row(Modifier.fillMaxSize()) {
                        SidePanel()
                        MainPanel()
                    }
                }
            }
        }
    }
}
