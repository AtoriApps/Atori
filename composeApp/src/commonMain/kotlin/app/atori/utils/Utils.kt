@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package app.atori.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import app.atori.databases.AtoriDatabase
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.resources.*
import org.jxmpp.jid.impl.JidCreate
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

object NavigatorUtils {
    fun NavHostController.naviIfNotHere(destination: String) {
        println("Current destination: ${currentDestination?.route}, destination: $destination")
        if (currentDestination?.route != destination) {
            navigate(destination) /*{
                popUpTo(graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }*/
        }
    }
}

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

expect object MultiplatformIO {
    fun getAtoriDbBuilder(): RoomDatabase.Builder<AtoriDatabase>
}

// TODO: 实现一个通用Log工具？

object DatabaseUtils {
    fun RoomDatabase.Builder<AtoriDatabase>.buildDb(): AtoriDatabase = this
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

    const val DATABASE_NAME = "atori_multi.db"
}

object XmppUtils {
    val String.jid
        get() = JidCreate.from(this)
}

object ComposeUtils {
    fun Color.opacify(opacity: Float): Color {
        val alpha = this.alpha * opacity
        return Color(alpha = alpha, red = red, green = green, blue = blue)
    }

    val Int.dpPx: Float
        @Composable
        get() = LocalDensity.current.run { this@dpPx.dp.toPx() }

    val Float.pxRound: Int
        @Composable get() = (this + 0.5f).toInt()

    @Composable
    fun Modifier.paddingForSystemBars(): Modifier =
        Modifier.padding(WindowInsets.systemBars.asPaddingValues())
}

object ResUtils {
    val DrawableResource.vector
        @Composable
        get() = vectorResource(this)

    val DrawableResource.imgBmp
        @Composable
        get() = imageResource(this)

    /*    @Composable
    fun (@receiver:DrawableRes Int).painter(useInternalWay: Boolean = true) =
        if (useInternalWay) painterResource(id = this) else this.drawable.painter

    val Drawable.painter
        @Composable get() = rememberDrawablePainter(this)

    val (@receiver:DrawableRes Int).drawable
        get() = ResourcesCompat.getDrawable(
            appContext.resources,
            this,
            appContext.theme
        ) ?: throw NotFoundException("Drawable for Id $this not found")*/

    @Composable
    fun StringResource.text(vararg format: String): String = stringResource(this, *format)

    val StringResource.text
        @Composable
        get() = this.text()
}

/*object SPUtils {
    private const val TAG = "SPUtils"

    private val preferences: SharedPreferences get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun setString(key: String, value: String) = preferences.edit().putString(key, value).apply()

    fun getString(key: String, defaultValue: String? = null): String? =
        if (!preferences.contains(key)) {
            defaultValue?.let { setString(key, it) }
            defaultValue
        } else preferences.getString(key, defaultValue)


    fun setInt(key: String, value: Int) = preferences.edit().putInt(key, value).apply()

    fun getInt(key: String, defaultValue: Int = 0): Int =
        if (!preferences.contains(key)) {
            setInt(key, defaultValue)
            defaultValue
        } else preferences.getInt(key, defaultValue)


    fun setBoolean(key: String, value: Boolean) = preferences.edit().putBoolean(key, value).apply()

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        if (!preferences.contains(key)) {
            setBoolean(key, defaultValue)
            defaultValue
        } else preferences.getBoolean(key, defaultValue)

    fun remove(key: String) = preferences.edit().remove(key).apply()

    fun clear() = preferences.edit().clear().apply()
}*/

/*
object CompatUtils {
    fun XmppActivity.openOldChatPage(conversation: Conversation) {
        val intent = Intent(this, ConversationsActivity::class.java)
            .setAction(ConversationsActivity.ACTION_VIEW_CONVERSATION)
            .putExtra(ConversationsActivity.EXTRA_FROM_NEW, true)
            .putExtra(ConversationsActivity.EXTRA_CONVERSATION, conversation.uuid)

        startActivity(intent)
    }

    @Composable
    fun XmppActivity.getAvatarBitmapFor(avatarable: Avatarable, sizeInDp: Int): ImageBitmap? {
        // 缺省图片还没做
        // md异步drawable
        val sizeInPx = sizeInDp.dpPx.pxRound
        return avatarService().get(avatarable, sizeInPx, false)?.asImageBitmap()
    }

    // 狠狠地把状态栏、导航栏弄美丽
    fun Activity.makeActivityBarsFit(useDarkTheme: Boolean = isDarkMode) {
        // 使内容显示在状态栏和导航栏区域
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 设置状态栏和导航栏为透明
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        // 控制系统栏的外观
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = !useDarkTheme
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = !useDarkTheme
    }

    val isSystemInDarkMode: Boolean
        get() = appContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES


    // 读取SP并解析颜色模式
    val isDarkMode: Boolean
        get() {
            // 读SP并解析
            return AtoriApp.getSPACEnumAppearanceMode().getIsDarkModeFromACEnumAndSys()
        }

    fun Int.getIsDarkModeFromACEnumAndSys(): Boolean {
        return when (this) {
            AppCompatDelegate.MODE_NIGHT_YES -> true
            AppCompatDelegate.MODE_NIGHT_NO -> false
            else -> isSystemInDarkMode
        }
    }

    fun PackageManager.enableComponent(componentName: ComponentName) {
        setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    fun PackageManager.disableComponent(componentName: ComponentName) {
        setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    fun switchIcon(isDynamicColors: Boolean = false) {
        val pm = appContext.packageManager
        val defaultComponent = ComponentName(appContext, "app.atori.experimental.ui.SplashActivity")
        val dynamicComponent = ComponentName(appContext, "app.atori.experimental.ui.MainActivityAliasDynamic")

        if (isDynamicColors) {
            pm.enableComponent(dynamicComponent)
            pm.disableComponent(defaultComponent)
        } else {
            pm.enableComponent(defaultComponent)
            pm.disableComponent(dynamicComponent)
        }
    }

    fun String.toast() = ToastCompat.makeText(appContext, this, ToastCompat.LENGTH_SHORT).show()

    fun setACEnumAppearanceMode(desiredNightMode: Int) = AppCompatDelegate.setDefaultNightMode(desiredNightMode)
}*/
