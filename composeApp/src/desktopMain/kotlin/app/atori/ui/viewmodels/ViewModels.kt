package app.atori.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.lifecycle.ViewModel
import app.atori.utils.MultiplatformLogUtils

class SidePanelViewModel(): ViewModel() {
    val sidePanelExpanded = mutableStateOf(true)
}

class MainWindowViewModel(): ViewModel() {
    companion object{
        private const val TAG = "MainWindowViewModel"
    }

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
            MultiplatformLogUtils.d(TAG, "窗口绑定正确")
        }.onFailure {
            ensured = false
            MultiplatformLogUtils.e(TAG, "窗口绑定错误", it)
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