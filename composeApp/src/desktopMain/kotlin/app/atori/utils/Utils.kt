package app.atori.utils

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.room.Room
import androidx.room.RoomDatabase
import app.atori.data.APP_DATABASE_NAME
import app.atori.data.local.databases.AppDatabase
import java.io.File

actual object MultiplatformLogUtils {
    actual fun d(tag: String, vararg messages: Any?) = printLog("D", tag, messages)

    actual fun i(tag: String, vararg messages: Any?) = printLog("I", tag, messages)

    actual fun w(tag: String, vararg messages: Any?) = printLog("W", tag, messages)

    actual fun e(tag: String, vararg messages: Any?) = if (messages.isNotEmpty() && messages.last() is Throwable) {
        val throwable = messages.last() as Throwable

        // 拼接除了最后一个 Throwable 之外的所有信息
        println("[E] $tag > ${messages.dropLast(1).joinToString(" ") { it.toString() }}")

        throwable.printStackTrace()  // 输出详细堆栈信息
    } else printLog("E", tag, messages)

    private fun printLog(level: String, tag: String, messages: Array<out Any?>) {
        println("[$level] $tag > ${messages.joinToString(" ") { it.toString() }}")
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object MultiplatformFunctions {
    // 最终还是按照安卓的规矩存放
    actual fun getAppDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = File(getAppDataPath(), "databases/$APP_DATABASE_NAME")
        return Room.databaseBuilder<AppDatabase>(name = dbFile.absolutePath)
    }

    actual fun getAppFilesPath(): File = File(getAppDataPath(), "files")

    @OptIn(ExperimentalComposeUiApi::class)
    actual fun maybeHover(modifier: Modifier, hoverHandler: ComposeUtils.HoverHandler): Modifier =
        modifier.onPointerEvent(PointerEventType.Enter) {
            hoverHandler.onEnter()
        }.onPointerEvent(PointerEventType.Exit) {
            hoverHandler.onExit()
        }

    private fun getAppDataPath(): File = File(System.getProperty("user.home"), "app.tele")
}