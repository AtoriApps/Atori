package app.atori

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
        KoinApplication(application = {
            modules(androidAppModule)
        }) {
            // 创建根导航控制器
            val rootNaviController = rememberNavController()
            val navBackStackEntry by rootNaviController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // 判断当前是否在主界面，可以根据路由判断
            val isMainScreen = currentRoute == "main"

            val demoState = koinInject<DemoState>()

            LaunchedEffect(demoState.currentChat.value) {
                if (demoState.currentChat.value != -1) rootNaviController.navigate("chat") {
                    // 保留主界面在栈底
                    launchSingleTop = true
                }
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