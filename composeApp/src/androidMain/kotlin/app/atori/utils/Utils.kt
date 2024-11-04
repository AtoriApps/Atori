package app.atori.utils

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
}