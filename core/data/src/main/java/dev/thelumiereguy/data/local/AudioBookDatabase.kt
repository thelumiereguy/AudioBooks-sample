package dev.thelumiereguy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.thelumiereguy.data.local.dao.AudioBookDao
import dev.thelumiereguy.data.local.models.AudioBookEntity

@Database(entities = [AudioBookEntity::class], version = 1)
abstract class AudioBookDatabase : RoomDatabase() {
    abstract fun audioBookDao(): AudioBookDao
}
