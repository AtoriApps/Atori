package app.atori.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.atori.utils.NavigatorUtils.naviIfNotHere
import app.atori.viewModels.XmppViewModel

const val DestOverview = "overview"
const val DestChat = "chat"
const val DestLogin = "login"

@Composable
fun AtoriNavHost(navi: NavHostController, xmppViewModel: XmppViewModel) {
    val noAccount by xmppViewModel.noAccount.collectAsState(initial = false)

    LaunchedEffect(noAccount) {
        if (noAccount) navi.naviIfNotHere(DestLogin)
        else navi.naviIfNotHere(DestOverview)
    }


    NavHost(navi, DestOverview,Modifier.clip(MaterialTheme.shapes.large)) {
        /*composable(DestOverview) { OverviewPage(xmppViewModel) }
        composable(DestLogin) { LoginPage(xmppViewModel *//* ... *//*) }*/
        // Add more destinations similarly.
    }
}