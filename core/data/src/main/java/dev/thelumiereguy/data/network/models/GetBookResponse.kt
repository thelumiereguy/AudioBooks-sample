package dev.thelumiereguy.data.network.models

data class GetBookResponse(
    val data: Data
) {
    data class Data(
        val books: List<Book>
    ) {
        data class Book(
            val id: Long
        )
    }
}