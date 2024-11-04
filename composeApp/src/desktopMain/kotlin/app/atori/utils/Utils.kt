package app.atori.utils

import androidx.room.Room
import androidx.room.RoomDatabase
import app.atori.databases.AtoriDatabase
import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object MultiplatformIO {
    actual fun getAtoriDbBuilder(): RoomDatabase.Builder<AtoriDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), DatabaseUtils.DATABASE_NAME)
        return Room.databaseBuilder<AtoriDatabase>(
            name = dbFile.absolutePath,
        )
    }
}