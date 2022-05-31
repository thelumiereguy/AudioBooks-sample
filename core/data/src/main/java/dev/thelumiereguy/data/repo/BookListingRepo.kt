package dev.thelumiereguy.data.repo

import dev.thelumiereguy.data.models.AudioBook
import kotlinx.coroutines.flow.Flow

interface BookListingRepo {
    suspend fun observeAudioBooks(): Flow<List<AudioBook>?>
    suspend fun refreshAudioBooks()
}
