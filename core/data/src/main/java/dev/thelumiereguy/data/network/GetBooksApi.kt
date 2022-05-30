package dev.thelumiereguy.data.network

import dev.thelumiereguy.data.network.models.GetBookResponse

interface GetBooksApi {
    suspend fun getBooks(): GetBookResponse
}