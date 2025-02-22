package app.atori.misc

import androidx.compose.runtime.Composable
import app.atori.resources.Res
import app.atori.resources.ic_chats_24px
import app.atori.resources.ic_chats_filled_24px
import app.atori.resources.ic_contacts_24px
import app.atori.resources.ic_contacts_filled_24px
import app.atori.resources.ic_explore_24px
import app.atori.resources.ic_explore_filled_24px
import app.atori.resources.img_avatar_demo
import app.atori.ui.models.NavTabItem
import app.atori.utils.ResUtils.imgBmp

object DemoConstants {
    val userName = "Earzu Chan"

    val userPlatform = "Xmpp"

    val userJid = "ezc@atori.im"

    val userAvatar
        @Composable
        get() = Res.drawable.img_avatar_demo.imgBmp

    val navTabItems = listOf(
        NavTabItem(Res.drawable.ic_chats_24px, Res.drawable.ic_chats_filled_24px, "聊天", "chats"),
        NavTabItem(Res.drawable.ic_contacts_24px, Res.drawable.ic_contacts_filled_24px, "联系人", "contacts"),
        NavTabItem(Res.drawable.ic_explore_24px, Res.drawable.ic_explore_filled_24px, "发现", "explore"),
    )
}