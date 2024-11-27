package app.atori

import androidx.compose.runtime.Composable
import app.atori.misc.ComposeActivity
import app.atori.ui.screens.DemoMainScreen


class InitAppActivity : ComposeActivity() {
    @Composable
    override fun Content() = DemoMainScreen()
}