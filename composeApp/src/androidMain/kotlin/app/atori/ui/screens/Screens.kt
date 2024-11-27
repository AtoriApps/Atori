package app.atori.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.atori.stores.UniversalStateStore
import app.atori.ui.pages.DemoChatPage
import app.atori.ui.views.DemoMainScreenBottomBar
import app.atori.ui.views.DemoMainScreenTopBar

@Composable
fun DemoMainScreen() = Scaffold(topBar = { DemoMainScreenTopBar() }, bottomBar = { DemoMainScreenBottomBar() })
{ padding ->
    Box(Modifier.padding(padding)) {
        if (UniversalStateStore.demoCurrentChat.value != -1) DemoChatPage()
        else UniversalStateStore.navTabItems[UniversalStateStore.currentNavTab.value].view()
    }
}