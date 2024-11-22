package app.atori.stores

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import org.jivesoftware.smack.android.AndroidSmackInitializer

object AndroidAppStateStore {
    var inited = false

    lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context

        runCatching {
            AndroidSmackInitializer.initialize(appContext)
        }.onSuccess {
            inited = true
            println("安卓初始化成功")
        }.onFailure {
            println("安卓初始化失败\n${it.stackTraceToString()}")
            inited = false
        }
    }

    private fun <T> ensureInited(block: () -> T): T =
        if (inited) block() else throw IllegalStateException("安卓初始化异常")
}