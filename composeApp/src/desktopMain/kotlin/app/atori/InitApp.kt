package app.atori

import androidx.compose.ui.window.application
import app.atori.di.desktopAppModule
import app.atori.ui.windows.MainWindow
import org.koin.compose.KoinApplication

fun main() = application {
    KoinApplication(application = {
        modules(desktopAppModule)
    }) {
        MainWindow(this)
    }
}