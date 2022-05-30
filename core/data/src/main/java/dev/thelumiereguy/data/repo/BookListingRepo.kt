package dev.thelumiereguy.data.repo

import dev.thelumiereguy.data.models.Book

interface BookListingRepo {
    suspend fun fetchBooks(): List<Book>
}