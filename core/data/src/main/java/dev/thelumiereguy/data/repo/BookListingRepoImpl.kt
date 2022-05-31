package dev.thelumiereguy.data.repo

import dev.thelumiereguy.data.local.dao.AudioBookDao
import dev.thelumiereguy.data.local.mapper.mapResponseToEntity
import dev.thelumiereguy.data.mapper.BookCoverToDrawableMapper
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

    override suspend fun observeAudioBooks(): Flow<List<AudioBook>?> {
        return audioBookDao.getAudioBooksFlow()
            .map { audioBookList ->
                audioBookList?.map { bookModel ->
                    AudioBook(
                        bookModel.book_id,
                        bookModel.bookName,
                        bookModel.bookAuthor,
                        bookModel.bookCoverDrawable,
                        bookModel.bookAudioUrl,
                    )
                }
            }.flowOn(dispatcherProvider.io)
    }

    override suspend fun refreshAudioBooks() {
        supervisorScope {
            withContext(dispatcherProvider.io) {
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
}
