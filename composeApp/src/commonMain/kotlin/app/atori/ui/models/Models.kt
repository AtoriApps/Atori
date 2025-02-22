package app.atori.ui.models

import app.atori.resources.Res
import app.atori.resources.ic_chats_24px
import app.atori.resources.ic_chats_filled_24px
import app.atori.resources.ic_contacts_24px
import app.atori.resources.ic_contacts_filled_24px
import app.atori.resources.ic_explore_24px
import app.atori.resources.ic_explore_filled_24px
import org.jetbrains.compose.resources.DrawableResource

data class NavTabItem(
    val icon: DrawableResource,
    val selectedIcon: DrawableResource,
    val name: String,
    val route: String
)