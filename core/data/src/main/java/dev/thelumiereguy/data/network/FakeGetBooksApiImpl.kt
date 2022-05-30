package dev.thelumiereguy.data.network

import dev.thelumiereguy.data.network.models.GetBookResponse

class FakeGetBooksApiImpl : GetBooksApi {
    override suspend fun getBooks(): GetBookResponse {
        TODO()
    }
}