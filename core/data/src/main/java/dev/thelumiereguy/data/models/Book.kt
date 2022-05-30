package dev.thelumiereguy.data.models

import androidx.annotation.DrawableRes

data class Book(
    val bookId: Long,
    val bookName: String,
    val bookAuthor: String,
    @DrawableRes val bookCoverDrawable: Int,
    val bookAudioUrl: String,
)
