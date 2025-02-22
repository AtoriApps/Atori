package app.atori.data.local.databases

import androidx.room.*
import app.atori.data.local.models.AccountEntity
import app.atori.data.local.models.MessageEntity
import kotlinx.coroutines.flow.Flow

@Database(entities = [MessageEntity::class, AccountEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun accountDao(): AccountDao
}

@Dao
interface AccountDao {
}

@Dao
interface MessageDao {
}

