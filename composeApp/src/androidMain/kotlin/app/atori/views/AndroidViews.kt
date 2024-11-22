package app.atori.views

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
import app.atori.resources.Res
import app.atori.resources.ic_search_24px
import app.atori.resources.img_avatar_demo
import app.atori.stores.UniversalStateStore
import app.atori.utils.ResUtils.imgBmp
import app.atori.utils.ResUtils.vector
import app.atori.views.dialogs.AboutDialog
import app.atori.views.dialogs.DialogBase
import app.atori.views.dialogs.OneAtoriDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopBar() {
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
                TopBarAtoriIcon()
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
            TopBarControlButton(Res.drawable.ic_search_24px, "Search") {}

            TopBarControlButton({
                showOneAtori = true
            }) {
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
fun MainScreenBottomBar() = NavigationBar {
    UniversalStateStore.navTabItems.forEachIndexed { index, item ->
        NavigationBarItem(
            icon = {
                Icon(
                    (if (UniversalStateStore.currentNavTab.value == index) item.selectedIcon else item.icon).vector,
                    item.name
                )
            },
            label = { Text(item.name) },
            selected = UniversalStateStore.currentNavTab.value == index,
            onClick = { UniversalStateStore.currentNavTab.value = index },
            alwaysShowLabel = false
        )
    }
}