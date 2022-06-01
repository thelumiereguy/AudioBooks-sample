package dev.thelumiereguy.data.mapper

import dev.thelumiereguy.data.local.models.AudioBookEntity
import dev.thelumiereguy.data.models.AudioBook

fun AudioBookEntity.mapBookEntityToDomainModel(bookCoverToDrawableMapper: BookCoverToDrawableMapper): AudioBook {
    return AudioBook(
        book_id,
        bookName,
        bookAuthor,
        bookCoverToDrawableMapper.getDrawable(bookName),
        bookAudioUrl,
        progress
    )
}
