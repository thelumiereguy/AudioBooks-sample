package dev.thelumiereguy.data.repo

import dev.thelumiereguy.data.mapper.BookCoverToDrawableMapper
import dev.thelumiereguy.data.models.Book
import dev.thelumiereguy.data.network.GetBooksApi
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.withContext

class BookListingRepoImpl @Inject constructor(
    private val booksApi: GetBooksApi,
    private val bookCoverToDrawableMapper: BookCoverToDrawableMapper,
    private val dispatcherProvider: DispatcherProvider
) : BookListingRepo {

    override suspend fun fetchBooks(): List<Book> {
        return withContext(dispatcherProvider.io) {
            try {
                val response = booksApi.getBooks()
                response.data.books.map { bookModel ->
                    Book(
                        bookModel.id,
                        bookModel.bookName,
                        bookModel.authorName,
                        bookCoverToDrawableMapper.getDrawable(bookModel.bookName),
                        bookModel.bookAudioUrl,
                    )
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
                emptyList()
            }
        }
    }
}