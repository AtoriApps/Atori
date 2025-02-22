package app.atori

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.atori.data.states.DemoState
import app.atori.di.androidAppModule
import app.atori.misc.ComposeActivity
import app.atori.ui.screens.*
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject


class InitAppActivity : ComposeActivity() {
    companion object {
        private const val TAG = "InitAppActivity"
    }

    @Composable
    override fun Content() {
        // TODO：IMPL in NaVi
        KoinApplication(application = {
            modules(androidAppModule)
        }) {
            // 创建根导航控制器
            val rootNaviController = rememberNavController()

            val demoState = koinInject<DemoState>()

            LaunchedEffect(demoState.currentChat.value) {
                if (demoState.currentChat.value != -1) rootNaviController.navigate("chat")
            }

            NavHost(navController = rootNaviController, startDestination = "main") {
                // 主界面，带有内部的选项卡导航
                composable("main") {
                    DemoMainScreen()
                }

                composable("chat") {
                    DemoChatScreen(rootNaviController)
                }

                composable("chat_details") {
                    DemoChatDetailsScreen(rootNaviController)
                }

                composable("video_call") {
                    DemoVideoCallScreen(rootNaviController)
                }

                composable("voice_call") {
                    DemoVoiceCallScreen(rootNaviController)
                }

                composable("incoming_call") {
                    DemoIncomingCallScreen(rootNaviController)
                }
            }
        }
    }
}