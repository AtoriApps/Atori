package app.atori.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.atori.resources.Res
import app.atori.resources.ic_search_24px
import app.atori.stores.UniversalStateStore
import app.atori.utils.ResUtils.vector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopBar() = Box(
    Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surfaceContainer)
        .windowInsetsPadding(TopAppBarDefaults.windowInsets)
        .height(64.dp)
        .padding(horizontal = 4.dp)
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
    }
}

@Composable
fun MainScreenBottomBar() {
    NavigationBar {
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
                alwaysShowLabel = UniversalStateStore.currentNavTab.value == index
            )
        }
    }
}