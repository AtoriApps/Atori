package app.atori.multi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import app.atori.multi.utils.ResUtils.text
import app.atori.multi.views.AtoriWindowTitleBar
import atorimulti.composeapp.generated.resources.Res
import atorimulti.composeapp.generated.resources.app_name
import atorimulti.composeapp.generated.resources.ic_atori_icon
import atorimulti.composeapp.generated.resources.ic_atori_icon_dark
import org.jetbrains.compose.resources.painterResource

@Composable
fun AtoriMainWindow(appScope: ApplicationScope) {
    val windowState = rememberWindowState()
    Window(
        state = windowState,
        icon = if (isSystemInDarkTheme()) painterResource(Res.drawable.ic_atori_icon) else painterResource(Res.drawable.ic_atori_icon_dark),
        onCloseRequest = appScope::exitApplication,
        title = Res.string.app_name.text,
        undecorated = true,
        transparent = true
    ) {
        AtoriMainWindowDelegate.setMainWindowInstance(appScope, this@Window, windowState)

        AtoriMultiTheme {
            val rcs17 = RoundedCornerShape(17.dp)
            val rcs0 = RoundedCornerShape(0.dp)
            Column(
                // 窗口基本样式
                Modifier.fillMaxSize()
                    .clip(if (AtoriMainWindowDelegate.isMaximized) rcs0 else rcs17)
                    .border(
                        1.dp,
                        if (AtoriMainWindowDelegate.isMaximized) Color.Transparent else MaterialTheme.colorScheme.outlineVariant,
                        rcs17
                    )
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(if (AtoriMainWindowDelegate.isMaximized) 0.dp else 1.dp)
                // FIXME: 以后限制窗口大小
            ) {
                AtoriWindowTitleBar()
            }
        }
    }
}

object AtoriMainWindowDelegate {
    private var ensured = false

    private lateinit var mAppScope: ApplicationScope
    private lateinit var mWindowScope: FrameWindowScope
    private lateinit var mWindowState: WindowState

    fun setMainWindowInstance(appScope: ApplicationScope, windowScope: FrameWindowScope, windowState: WindowState) {
        /*if (ensured) {
            println("防抖")
            return
        } else {*/
        mAppScope = appScope
        mWindowScope = windowScope
        mWindowState = windowState

        println("窗口实例已设置")
        ensured = true
        /*}*/
    }

    fun close() = ensureWindow {
        mAppScope.exitApplication()
    }

    private fun <T> ensureWindow(block: () -> T): T =
        if (ensured) block() else throw IllegalStateException("窗口实例缺失")

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