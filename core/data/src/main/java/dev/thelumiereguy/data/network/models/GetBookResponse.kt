package dev.thelumiereguy.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetBookResponse(
    val data: Data
) {
    @Serializable
    data class Data(
        val books: List<Book>
    ) {
        @Serializable
        data class Book(
            @SerialName("book_id")
            val id: Long,
            @SerialName("book_name")
            val bookName: String,
            @SerialName("author_name")
            val authorName: String,
            @SerialName("book_audio_url")
            val bookAudioUrl: String,
        )
    }
}
