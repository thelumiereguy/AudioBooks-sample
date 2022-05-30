package dev.thelumiereguy.data

import dev.thelumiereguy.data.models.Book

interface BookListingRepo {
    suspend fun fetchBooks(): List<Book>
}