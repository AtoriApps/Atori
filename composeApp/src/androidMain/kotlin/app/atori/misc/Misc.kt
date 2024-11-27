package app.atori.misc

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import app.atori.ui.AndroidAtoriTheme
import app.atori.ui.screens.DemoMainScreen
import app.atori.stores.AndroidAppStateStore

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidAppStateStore.init(applicationContext)
    }
}

abstract class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AndroidAtoriTheme {
                Content()
            }
        }
    }

    @Composable
    abstract fun Content()
}