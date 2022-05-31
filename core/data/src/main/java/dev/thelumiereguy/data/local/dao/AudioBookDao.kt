package dev.thelumiereguy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.thelumiereguy.data.local.models.AudioBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AudioBookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(list: List<AudioBookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(audioBookEntity: AudioBookEntity)

    @Query("SELECT * from audio_books")
    abstract fun getAudioBooksFlow(): Flow<List<AudioBookEntity>?>

    @Query("SELECT * from audio_books WHERE book_author LIKE '%' || :searchString || '%' or book_name LIKE '%' || :searchString || '%'")
    abstract fun selectAudioBooks(searchString: String): Flow<List<AudioBookEntity>?>
}
