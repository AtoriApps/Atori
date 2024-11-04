package app.atori.databases

import androidx.room.*
import app.atori.entities.AccountEntity
import app.atori.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Database(entities = [MessageEntity::class, AccountEntity::class], version = 2)
abstract class AtoriDatabase : RoomDatabase() {
    abstract fun getMessageDao(): MessageDao

    abstract fun getAccountDao(): AccountDao
}

@Dao
interface AccountDao {
    @Insert
    suspend fun insertAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): Flow<List<AccountEntity>>
}

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Insert
    suspend fun insertMessage(message: MessageEntity)
}

