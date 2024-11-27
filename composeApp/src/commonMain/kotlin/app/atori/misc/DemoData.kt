package app.atori.misc

import androidx.compose.runtime.Composable
import app.atori.resources.Res
import app.atori.resources.img_avatar_demo
import app.atori.utils.ResUtils.imgBmp

object DemoData {
    val userName = "Earzu Chan"

    val userJid = "ezc@atori.im"

    val userAvatar
        @Composable
        get() = Res.drawable.img_avatar_demo.imgBmp
}