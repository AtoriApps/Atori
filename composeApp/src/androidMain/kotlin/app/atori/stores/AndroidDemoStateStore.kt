package app.atori.stores

import androidx.compose.runtime.mutableStateOf

object AndroidDemoStateStore {
    val isInChatInfoScreen = mutableStateOf(false)
    val isInCallScreen = mutableStateOf(false)
}