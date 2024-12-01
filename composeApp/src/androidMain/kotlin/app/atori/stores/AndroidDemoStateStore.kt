package app.atori.stores

import androidx.compose.runtime.mutableStateOf

object AndroidDemoStateStore {
    val isInChatInfoScreen = mutableStateOf(false)
    val isInVoiceCallScreen = mutableStateOf(false)
    val isInVideoCallScreen = mutableStateOf(false)
}