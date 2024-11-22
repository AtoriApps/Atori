package app.atori.utils

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.room.Room
import androidx.room.RoomDatabase
import app.atori.databases.AtoriDatabase
import app.atori.stores.AndroidAppStateStore


actual object MultiplatformIO {
    actual fun getAtoriDbBuilder(): RoomDatabase.Builder<AtoriDatabase> {
        val dbFile = AndroidAppStateStore.appContext.getDatabasePath(DatabaseUtils.DATABASE_NAME)
        return Room.databaseBuilder<AtoriDatabase>(
            context = AndroidAppStateStore.appContext,
            name = dbFile.absolutePath
        )
    }

    actual fun maybeHover(modifier: Modifier, hoverHandler: ComposeUtils.HoverHandler): Modifier {
        println("安卓平台没有指针移动过滤器")
        return modifier
    }
}