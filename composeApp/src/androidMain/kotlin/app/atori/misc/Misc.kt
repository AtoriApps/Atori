package app.atori.misc

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import app.atori.ui.AndroidAtoriTheme
import org.jivesoftware.smack.android.AndroidSmackInitializer

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()

        appContext = this

        runCatching {
            AndroidSmackInitializer.initialize(this)
        }
    }

    companion object{
        lateinit var appContext: AndroidApp
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