package dev.thelumiereguy.data.repo

import androidx.lifecycle.LiveData
import dev.thelumiereguy.data.models.AudioBook
import kotlinx.coroutines.flow.Flow

interface BookListingRepo {
    fun observeAudioBooks(): Flow<List<AudioBook>?>
    fun getAudioBookDetails(bookId: Long): LiveData<AudioBook?>
    suspend fun refreshAudioBooks()
    fun searchAudioBooks(searchString: String): Flow<List<AudioBook>?>
    suspend fun updateAudioBookProgress(bookId: Long, progress: Int)
}
