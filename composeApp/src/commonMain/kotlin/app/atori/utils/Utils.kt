@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package app.atori.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import app.atori.data.APP_PREFERENCES_NAME
import app.atori.data.local.databases.AppDatabase
import app.atori.ui.ColorSet
import kotlinx.coroutines.Dispatchers
import okio.Path.Companion.toOkioPath
import org.jetbrains.compose.resources.*
import org.jxmpp.jid.impl.JidCreate
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

expect object MultiplatformLogUtils {
    fun d(tag: String, vararg messages: Any?)
    fun i(tag: String, vararg messages: Any?)
    fun w(tag: String, vararg messages: Any?)
    fun e(tag: String, vararg messages: Any?)
}

// OLD
object TimestampUtils {
    private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    val Long.timeStr: String
        get() = timeStr(DATE_FORMAT)

    fun Long.timeStr(format: String): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val date = Date(this)
        return sdf.format(date)
    }

    fun String.timestamp(format: String): Long {
        val formatter = DateTimeFormatter.ofPattern(format)
        val localDateTime = LocalDateTime.parse(this, formatter)
        return localDateTime.toEpochSecond(ZoneOffset.UTC)
    }

    val String.timestamp: Long
        get() = timestamp(DATE_FORMAT)
}

expect object MultiplatformFunctions {
    fun getAppDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

    fun getAppFilesPath(): File

    internal fun maybeHover(modifier: Modifier, hoverHandler: ComposeUtils.HoverHandler): Modifier
}

object DataUtils {
    fun getAppDatabase(): AppDatabase = MultiplatformFunctions.getAppDatabaseBuilder()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

    fun getAppPreferences(): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        produceFile = { File(MultiplatformFunctions.getAppFilesPath(), APP_PREFERENCES_NAME).toOkioPath() }
    )
}

object XmppUtils {
    val String.jid
        get() = JidCreate.from(this)
}

object ComposeUtils {
    @Composable
    inline fun Modifier.only(
        condition: Boolean,
        elseBlock: @Composable Modifier.() -> Modifier = { this },
        ifBlock: @Composable Modifier.() -> Modifier
    ): Modifier = if (condition) ifBlock() else elseBlock()

    inline fun Color.opacity(opacity: Float): Color {
        val newAlpha = alpha * opacity
        return this.copy(newAlpha)
    }

    val ColorSet.buttonColors: ButtonColors
        get() = ButtonColors(
            color,
            onColor,
            colorContainer,
            onColorContainer
        )

    inline val Int.dpPx: Float
        @Composable
        get() = LocalDensity.current.run { this@dpPx.dp.toPx() }

    inline val Float.pxRound: Int
        @Composable get() = (this + 0.5f).toInt()

    @Composable
    inline fun Modifier.paddingForSystemBars(): Modifier =
        Modifier.padding(WindowInsets.systemBars.asPaddingValues())

    fun Modifier.maybeHover(hoverHandler: HoverHandler): Modifier {
        return MultiplatformFunctions.maybeHover(this, hoverHandler)
    }

    open class HoverHandler {
        open fun onEnter(): Boolean = false

        open fun onExit(): Boolean = false
    }
}

object ResUtils {
    val DrawableResource.vector
        @Composable
        get() = vectorResource(this)

    val DrawableResource.imgBmp
        @Composable
        get() = imageResource(this)

    @Composable
    fun StringResource.text(vararg format: String): String = stringResource(this, *format)

    val StringResource.text
        @Composable
        get() = this.text()
}
