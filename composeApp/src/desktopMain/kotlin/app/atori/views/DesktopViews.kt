package app.atori.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.atori.components.AIconButton
import app.atori.components.TopBarAtoriIcon
import app.atori.windows.MainWindowDelegate
import app.atori.resources.*
import app.atori.utils.ResUtils.imgBmp
import app.atori.views.dialogs.AboutDialog
import app.atori.views.dialogs.DialogBase
import app.atori.views.dialogs.OneAtoriDialog

@Composable
fun MainWindowTopBar() {
    var showOneAtori by remember { mutableStateOf(false) }

    var showAbout by remember { mutableStateOf(false) }

    if (showOneAtori)
        DialogBase({ showOneAtori = false }) {
            OneAtoriDialog({ showAbout = true })
        }

    if (showAbout)
        DialogBase({ showAbout = false }) {
            AboutDialog()
        }

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
                    AIconButton(Res.drawable.ic_search_24px, "Search", 48) {}

                    AIconButton({
                        showOneAtori = true
                    }, 48) {
                        Image(
                            Res.drawable.img_avatar_demo.imgBmp,
                            "One Atori",
                            Modifier.size(32.dp).clip(CircleShape)
                        )
                    }

                    AIconButton(
                        Res.drawable.ic_minimize_24px,
                        "Minimize", 48
                    ) { MainWindowDelegate.isMinimized = true }

                    AIconButton(
                        if (MainWindowDelegate.isMaximized) Res.drawable.ic_leave_fullscreen_24px else Res.drawable.ic_fullscreen_24px,
                        "Maximize/Restore", 48
                    ) {
                        MainWindowDelegate.isMaximized = !MainWindowDelegate.isMaximized
                    }

                    AIconButton(Res.drawable.ic_close_24px, "Close", 48) { MainWindowDelegate.close() }
                }
            }
        }
    }
}