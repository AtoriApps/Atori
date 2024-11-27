package app.atori.models

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource


data class DemoChatModel(
    val avatar: DrawableResource,
    val name: String,
    val lastMessage: String,
    val timestamp: Long,
    val unreadCount: Int,
    val pinned: Boolean,
    val muted: Boolean,
    val sendState: Int
)

data class NavTabItem(
    val icon: DrawableResource,
    val selectedIcon: DrawableResource,
    val name: String,
    val view: @Composable () -> Unit
)

enum class DemoMessageType {
    FIRST_MESSAGE,
    HEADLESS,
    READ_TO_HERE,
    TIME_LINE,
    REPLY,
}

enum class DemoMessageBodyType {
    NONE,
    TEXT,
    IMAGE
}

data class DemoMessageReactionModel(
    val emoji: String,
    val count: Int = 1,
    val useClicked: Boolean = false
)

data class DemoMessageModel(
    // val fromNpc: Boolean = false,
    val messageBodyData: Any?,
    val timestamp: Long,
    val messageType: DemoMessageType,
    val messageBodyType: DemoMessageBodyType = DemoMessageBodyType.NONE,
    val reactions: List<DemoMessageReactionModel> = emptyList()
)