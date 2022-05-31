package dev.thelumiereguy.data.repo

import dev.thelumiereguy.data.models.AudioBook
import kotlinx.coroutines.flow.Flow

interface BookListingRepo {
    fun observeAudioBooks(): Flow<List<AudioBook>?>
    suspend fun refreshAudioBooks()
    fun searchAudioBooks(searchString: String): Flow<List<AudioBook>?>
}
