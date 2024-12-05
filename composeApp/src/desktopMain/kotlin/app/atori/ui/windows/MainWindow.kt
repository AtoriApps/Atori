package app.atori.ui.windows

import androidx.compose.foundation.background
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
import app.atori.ui.AtoriTheme
import app.atori.utils.ResUtils.text
import app.atori.resources.Res
import app.atori.resources.app_name
import app.atori.resources.ic_atori_icon
import app.atori.resources.ic_atori_icon_dark
import app.atori.ui.panels.MainPanel
import app.atori.ui.panels.SidePanel
import app.atori.ui.views.MainWindowTopBar
import app.atori.utils.ComposeUtils.van
import org.jetbrains.compose.resources.painterResource

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
        MainWindowDelegate.setMainWindowInstance(appScope, this@Window, windowState)

        AtoriTheme {
            val rcs17 = RoundedCornerShape(17.dp)

            Surface(
                Modifier.fillMaxSize()
                    // 窗口基本样式，另外发现这 Surface 和 IconButton 的主题色有关
                    .van(!MainWindowDelegate.isMaximized) { clip(rcs17) }
                    .background(MaterialTheme.colorScheme.surface)
                    .van(!MainWindowDelegate.isMaximized) {
                        border(1.dp, MaterialTheme.colorScheme.outlineVariant, rcs17)
                    }
                    .van(!MainWindowDelegate.isMaximized) { padding(1.dp) }
                // FIXME: 以后限制窗口大小
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

// 以后为了解耦可能还需要抽成单独的Window函数然后里面自管自己的窗口状态
object MainWindowDelegate {
    private var ensured = false

    private lateinit var mAppScope: ApplicationScope
    private lateinit var mWindowScope: FrameWindowScope
    private lateinit var mWindowState: WindowState

    fun setMainWindowInstance(
        appScope: ApplicationScope,
        windowScope: FrameWindowScope,
        windowState: WindowState
    ) {
        runCatching {
            mAppScope = appScope
            mWindowScope = windowScope
            mWindowState = windowState
        }.onSuccess {
            ensured = true
            println("窗口绑定正确")
        }.onFailure {
            ensured = false
            println("窗口绑定错误\n${it.stackTraceToString()}")
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
            mWindowState.placement =
                if (value) WindowPlacement.Maximized else WindowPlacement.Floating
        }
}