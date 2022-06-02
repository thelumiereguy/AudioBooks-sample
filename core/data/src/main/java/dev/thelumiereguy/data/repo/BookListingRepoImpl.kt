package dev.thelumiereguy.data.repo

import dev.thelumiereguy.data.local.dao.AudioBookDao
import dev.thelumiereguy.data.local.mapper.mapResponseToEntity
import dev.thelumiereguy.data.mapper.BookCoverToDrawableMapper
import dev.thelumiereguy.data.mapper.mapBookEntityToDomainModel
import dev.thelumiereguy.data.models.AudioBook
import dev.thelumiereguy.data.network.GetBooksApi
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

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
                    bookModel.mapBookEntityToDomainModel(bookCoverToDrawableMapper)
                }
            }.flowOn(dispatcherProvider.io)
    }

    override fun getAudioBookDetails(bookId: Long): Flow<AudioBook?> {
        return audioBookDao.findAudioBook(bookId).map {
            it?.mapBookEntityToDomainModel(bookCoverToDrawableMapper)
        }
    }

    override suspend fun refreshAudioBooks() {
        supervisorScope {
            withContext(dispatcherProvider.io) {
                // Artificial delay
                delay(100)
                try {
                    val response = booksApi.getBooks()
                    val audioBooksEntityList = response.data.books
                        .mapResponseToEntity()
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
                    bookModel.mapBookEntityToDomainModel(bookCoverToDrawableMapper)
                }
            }.flowOn(dispatcherProvider.io)
    }

    override suspend fun updateAudioBookProgress(bookId: Long, progress: Int) {
        withContext(dispatcherProvider.io) {
            audioBookDao.updateAudioBookProgress(bookId, progress)
        }
    }
}
