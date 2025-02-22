package app.atori.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.compose.resources.DrawableResource

// Demo 模型

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

// 数据库模型（实体）

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fromUser: String,
    val messageBody: String,
    val timestamp: Long
)

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jid: String,
    val password: String,
)