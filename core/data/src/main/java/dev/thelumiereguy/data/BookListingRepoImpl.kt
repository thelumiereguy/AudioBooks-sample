package dev.thelumiereguy.data

import dev.thelumiereguy.data.models.Book
import dev.thelumiereguy.data.network.GetBooksApi
import javax.inject.Inject

class BookListingRepoImpl @Inject constructor(
    private val booksApi: GetBooksApi
) : BookListingRepo {

    override suspend fun fetchBooks(): List<Book> {
        return booksApi.getBooks().data.books.map {
            Book(
                it.id,
                "",
                "",
                ""
            )
        }
    }
}