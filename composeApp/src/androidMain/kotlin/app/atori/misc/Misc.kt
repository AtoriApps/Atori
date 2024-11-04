package app.atori.misc

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import app.atori.AtoriTheme
import app.atori.screens.MainScreen
import app.atori.stores.AndroidAppStateStore

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidAppStateStore.init(applicationContext)
    }
}

class AndroidActivity : ComposeActivity() {
    @Composable
    override fun content() = MainScreen()
}

abstract class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AtoriTheme {
                content()
            }
        }
    }

    @Composable
    abstract fun content()
}