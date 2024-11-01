package app.atori.multi.windows

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import app.atori.multi.AtoriMultiTheme
import app.atori.multi.utils.ResUtils.text
import app.atori.multi.views.MainWindowTopBar
import app.atori.multi.views.panels.MainPanel
import app.atori.multi.views.panels.SidePanel
import atorimulti.composeapp.generated.resources.Res
import atorimulti.composeapp.generated.resources.app_name
import atorimulti.composeapp.generated.resources.ic_atori_icon
import atorimulti.composeapp.generated.resources.ic_atori_icon_dark
import org.jetbrains.compose.resources.painterResource

// 怎么确定只有单例
@Composable
fun ApplicationScope.MainWindow() {
    val windowState = rememberWindowState()
    Window(
        state = windowState,
        icon = if (isSystemInDarkTheme()) painterResource(Res.drawable.ic_atori_icon) else painterResource(Res.drawable.ic_atori_icon_dark),
        onCloseRequest = ::exitApplication,
        title = Res.string.app_name.text,
        undecorated = true,
        transparent = true
    ) {
        MainWindowDelegate.setMainWindowInstance(this@MainWindow, this@Window, windowState)

        AtoriMultiTheme {
            val rcs17 = RoundedCornerShape(17.dp)
            val rcs0 = RoundedCornerShape(0.dp)
            Surface(
                // 窗口基本样式，另外发现这 Surface 和 IconButton 的主题色有关
                Modifier.fillMaxSize().clip(if (MainWindowDelegate.isMaximized) rcs0 else rcs17)
                    .border(
                        1.dp,
                        if (MainWindowDelegate.isMaximized) Color.Transparent else MaterialTheme.colorScheme.outlineVariant,
                        rcs17
                    )
                    .padding(if (MainWindowDelegate.isMaximized) 0.dp else 1.dp)
                // FIXME: 以后限制窗口大小
            ) {
                Column(Modifier.fillMaxSize()) {
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

object MainWindowDelegate {
    private var ensured = false

    private lateinit var mAppScope: ApplicationScope
    private lateinit var mWindowScope: FrameWindowScope
    private lateinit var mWindowState: WindowState

    fun setMainWindowInstance(appScope: ApplicationScope, windowScope: FrameWindowScope, windowState: WindowState) {
        runCatching {
            mAppScope = appScope
            mWindowScope = windowScope
            mWindowState = windowState
        }.onSuccess {
            println("窗口绑定正确")
            ensured = true
        }.onFailure {
            println("窗口绑定错误\n${it.stackTraceToString()}")
            ensured = false
        }
    }

    fun close() = ensureWindow {
        mAppScope.exitApplication()
    }

    private fun <T> ensureWindow(block: () -> T): T =
        if (ensured) block() else throw IllegalStateException("窗口绑定异常")

    val windowScope: FrameWindowScope
        get() = ensureWindow { mWindowScope }

    var isMinimized: Boolean
        get() = ensureWindow { mWindowState.isMinimized }
        set(value) = ensureWindow {
            mWindowState.isMinimized = value
        }

    var isMaximized: Boolean
        get() = ensureWindow { mWindowState.placement == WindowPlacement.Maximized }
        set(value) = ensureWindow {
            mWindowState.placement = if (value) WindowPlacement.Maximized else WindowPlacement.Floating
        }
}