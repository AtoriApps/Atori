package app.atori.multi.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.atori.multi.windows.MainWindowDelegate
import app.atori.multi.utils.ResUtils.vector
import atorimulti.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun MainWindowTopBar() {
    MainWindowDelegate.windowScope.WindowDraggableArea {
        Row(Modifier.fillMaxWidth().height(56.dp)) {
            Box(Modifier.fillMaxHeight().width(64.dp)) {
                Icon(
                    Res.drawable.ic_atori_logo_24px.vector,
                    "Atori",
                    Modifier.size(24.dp).align(Alignment.Center),
                    MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(Modifier.fillMaxSize().padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Row(Modifier.weight(1F), Arrangement.spacedBy(4.dp)) {
                    TextButton({}) {
                        Text("Atori")
                    }
                }
                Row {
                    MainWindowTopBarControlButton({}, Res.drawable.ic_search_24px, "Search")
                    MainWindowTopBarControlButton(
                        { MainWindowDelegate.isMinimized = true },
                        Res.drawable.ic_minimize_24px,
                        "Minimize"
                    )
                    MainWindowTopBarControlButton(
                        {
                            MainWindowDelegate.isMaximized = !MainWindowDelegate.isMaximized
                        },
                        if (MainWindowDelegate.isMaximized) Res.drawable.ic_leave_fullscreen_24px else Res.drawable.ic_fullscreen_24px,
                        "Maximize/Restore"
                    )
                    MainWindowTopBarControlButton({ MainWindowDelegate.close() }, Res.drawable.ic_close_24px, "Close")
                }
            }
        }
    }
}

@Composable
fun MainWindowTopBarControlButton(onClick: () -> Unit, icon: DrawableResource, contentDescription: String) {
    // IconButton的主题色必须套在Surface里才能生效
    IconButton(onClick, Modifier.size(48.dp), true) {
        Icon(icon.vector, contentDescription, Modifier)
    }
}