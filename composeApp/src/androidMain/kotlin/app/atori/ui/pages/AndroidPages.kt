package app.atori.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.atori.ui.views.DemoChatInputBar
import app.atori.ui.views.DemoChatView

@Composable
fun DemoChatPage() = Column(Modifier.fillMaxSize()) {
    // DemoChatPanelTopBar() FIXME: 要有顶部栏

    Box(Modifier.weight(1F)) { DemoChatView() }
    DemoChatInputBar()
}