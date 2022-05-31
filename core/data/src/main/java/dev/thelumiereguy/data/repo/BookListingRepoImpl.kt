package dev.thelumiereguy.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import dev.thelumiereguy.data.local.dao.AudioBookDao
import dev.thelumiereguy.data.local.mapper.mapResponseToEntity
import dev.thelumiereguy.data.mapper.BookCoverToDrawableMapper
import dev.thelumiereguy.data.mapper.mapBookEntityToDomainModel
import dev.thelumiereguy.data.models.AudioBook
import dev.thelumiereguy.data.network.GetBooksApi
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class BookListingRepoImpl @Inject constructor(
    private val booksApi: GetBooksApi,
    private val audioBookDao: AudioBookDao,
    private val bookCoverToDrawableMapper: BookCoverToDrawableMapper,
    private val dispatcherProvider: DispatcherProvider
) : BookListingRepo {

    override fun observeAudioBooks(): Flow<List<AudioBook>?> {
        return audioBookDao.getAudioBooksFlow()
            .map { audioBookList ->
                audioBookList?.map { bookModel ->
                    bookModel.mapBookEntityToDomainModel()
                }
            }.flowOn(dispatcherProvider.io)
    }

    override fun getAudioBookDetails(bookId: Long): LiveData<AudioBook?> {
        return audioBookDao.findAudioBook(bookId).map {
            it?.mapBookEntityToDomainModel()
        }
    }

    override suspend fun refreshAudioBooks() {
        supervisorScope {
            withContext(dispatcherProvider.io) {
                delay(500)
                try {
                    val response = booksApi.getBooks()
                    val audioBooksEntityList = response.data.books
                        .mapResponseToEntity(bookCoverToDrawableMapper)
                    audioBookDao.insert(audioBooksEntityList)
                } catch (exception: IOException) {
                    exception.printStackTrace()
                }
            }
        }
    }

    override fun searchAudioBooks(searchString: String): Flow<List<AudioBook>?> {
        return audioBookDao.selectAudioBooks(searchString)
            .map { audioBookList ->
                audioBookList?.map { bookModel ->
                    bookModel.mapBookEntityToDomainModel()
                }
            }.flowOn(dispatcherProvider.io)
    }

    override suspend fun updateAudioBookProgress(bookId: Long, progress: Int) {
        withContext(dispatcherProvider.io) {
            audioBookDao.updateAudioBookProgress(bookId, progress)
        }
    }
}
