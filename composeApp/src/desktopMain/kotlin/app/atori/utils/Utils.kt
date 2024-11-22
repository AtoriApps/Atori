package app.atori.utils

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerMoveFilter
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

    @OptIn(ExperimentalComposeUiApi::class)
    actual fun maybeHover(modifier: Modifier, hoverHandler: ComposeUtils.HoverHandler): Modifier =
        modifier.onPointerEvent(PointerEventType.Enter) {
            hoverHandler.onEnter()
        }.onPointerEvent(PointerEventType.Exit) {
            hoverHandler.onExit()
        }
}