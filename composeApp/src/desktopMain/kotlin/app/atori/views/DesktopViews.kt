package app.atori.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.atori.windows.MainWindowDelegate
import app.atori.resources.*

@Composable
fun MainWindowTopBar() {
    MainWindowDelegate.windowScope.WindowDraggableArea {
        Row(Modifier.fillMaxWidth().height(56.dp)) {
            Box(Modifier.fillMaxHeight().width(64.dp), Alignment.Center) {
                TopBarAtoriIcon()
            }
            Row(Modifier.fillMaxSize().padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Row(Modifier.weight(1F), Arrangement.spacedBy(4.dp)) {
                    TextButton({}) {
                        Text("Atori")
                    }
                }
                Row {
                    TopBarControlButton(Res.drawable.ic_search_24px, "Search") {}

                    TopBarControlButton(
                        Res.drawable.ic_minimize_24px,
                        "Minimize"
                    ) { MainWindowDelegate.isMinimized = true }

                    TopBarControlButton(
                        if (MainWindowDelegate.isMaximized) Res.drawable.ic_leave_fullscreen_24px else Res.drawable.ic_fullscreen_24px,
                        "Maximize/Restore"
                    ) {
                        MainWindowDelegate.isMaximized = !MainWindowDelegate.isMaximized
                    }

                    TopBarControlButton(Res.drawable.ic_close_24px, "Close") { MainWindowDelegate.close() }
                }
            }
        }
    }
}