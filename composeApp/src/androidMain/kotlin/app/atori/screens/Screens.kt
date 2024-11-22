package app.atori.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.atori.pages.DemoChatPage
import app.atori.stores.UniversalStateStore
import app.atori.views.MainScreenBottomBar
import app.atori.views.MainScreenTopBar

@Composable
fun MainScreen() = Scaffold(topBar = { MainScreenTopBar() }, bottomBar = { MainScreenBottomBar() })
{ padding ->
    Box(Modifier.padding(padding)) {
        if (UniversalStateStore.demoCurrentChat.value != -1) DemoChatPage()
        else UniversalStateStore.navTabItems[UniversalStateStore.currentNavTab.value].view()
    }
}