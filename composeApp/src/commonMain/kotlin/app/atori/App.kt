package app.atori

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import app.atori.utils.DatabaseUtils.buildDb
import app.atori.viewModels.XmppViewModel
import app.atori.utils.MultiplatformIO

@Composable
fun AtoriApp() {
    val navi = rememberNavController()
    val xmppViewModel: XmppViewModel =
        viewModel { XmppViewModel(MultiplatformIO.getAtoriDbBuilder().buildDb()) }

    AtoriTheme {
        AtoriNavHost(navi, xmppViewModel)
    }
}