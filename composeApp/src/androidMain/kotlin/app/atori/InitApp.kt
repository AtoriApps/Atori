package app.atori

import androidx.compose.runtime.Composable
import app.atori.misc.ComposeActivity
import app.atori.stores.AndroidDemoStateStore
import app.atori.stores.DemoStateStore
import app.atori.ui.screens.DemoVoiceCallScreen
import app.atori.ui.screens.DemoChatDetailsScreen
import app.atori.ui.screens.DemoChatScreen
import app.atori.ui.screens.DemoIncomingCallScreen
import app.atori.ui.screens.DemoMainScreen
import app.atori.ui.screens.DemoVideoCallScreen


class InitAppActivity : ComposeActivity() {
    @Composable
    override fun Content() {
        if (AndroidDemoStateStore.isInIncomingCallScreen.value) DemoIncomingCallScreen()
        else if (AndroidDemoStateStore.isInVideoCallScreen.value) DemoVideoCallScreen()
        else if (AndroidDemoStateStore.isInVoiceCallScreen.value) DemoVoiceCallScreen()
        else if (AndroidDemoStateStore.isInChatInfoScreen.value) DemoChatDetailsScreen()
        else if (DemoStateStore.currentChat.value != -1) DemoChatScreen()
        else DemoMainScreen()
    }
}