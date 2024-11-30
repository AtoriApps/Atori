package app.atori

import androidx.compose.runtime.Composable
import app.atori.misc.ComposeActivity
import app.atori.stores.AndroidDemoStateStore
import app.atori.stores.DemoStateStore
import app.atori.ui.pages.DemoCallPage
import app.atori.ui.screens.DemoCallScreen
import app.atori.ui.screens.DemoChatDetailsScreen
import app.atori.ui.screens.DemoChatScreen
import app.atori.ui.screens.DemoMainScreen


class InitAppActivity : ComposeActivity() {
    @Composable
    override fun Content() {
        if (AndroidDemoStateStore.isInCallScreen.value) DemoCallScreen()
        else if (AndroidDemoStateStore.isInChatInfoScreen.value) DemoChatDetailsScreen()
        else if (DemoStateStore.currentChat.value != -1) DemoChatScreen()
        else DemoMainScreen()
    }
}