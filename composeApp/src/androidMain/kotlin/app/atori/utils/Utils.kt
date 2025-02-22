package app.atori.utils

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.room.Room
import androidx.room.RoomDatabase
import app.atori.data.APP_DATABASE_NAME
import app.atori.data.local.databases.AppDatabase
import app.atori.misc.AndroidApp
import java.io.File

private const val TAG = "AndroidUtils"

actual object MultiplatformLogUtils {
    actual fun d(tag: String, vararg messages: Any?) {
        Log.d(tag, messages.toStr())
    }

    actual fun i(tag: String, vararg messages: Any?) {
        Log.i(tag, messages.toStr())
    }

    actual fun w(tag: String, vararg messages: Any?) {
        Log.w(tag, messages.toStr())
    }

    actual fun e(tag: String, vararg messages: Any?) {
        if (messages.isNotEmpty() && messages.last() is Throwable) {
            val throwable = messages.last() as Throwable

            // 拼接除了最后一个 Throwable 之外的所有信息
            val msg = messages.dropLast(1).joinToString(" ") { it.toString() }

            Log.e(tag, msg, throwable)
        } else Log.e(tag, messages.toStr())
    }

    private fun Array<out Any?>.toStr() = joinToString(" ") { it.toString() }
}

actual object MultiplatformFunctions {
    actual fun getAppDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = AndroidApp.appContext.getDatabasePath(APP_DATABASE_NAME) // 在 /data/data/包名/databases/ 下
        return Room.databaseBuilder<AppDatabase>(
            context = AndroidApp.appContext,
            name = dbFile.absolutePath
        )
    }

    actual fun getAppFilesPath(): File = AndroidApp.appContext.filesDir // 在 /data/data/包名/files/ 下

    actual fun maybeHover(modifier: Modifier, hoverHandler: ComposeUtils.HoverHandler): Modifier {
        MultiplatformLogUtils.d(TAG, "安卓平台没有指针移动过滤器")
        return modifier
    }
}