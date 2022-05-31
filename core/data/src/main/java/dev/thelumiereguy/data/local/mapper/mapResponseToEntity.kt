package dev.thelumiereguy.data.local.mapper

import dev.thelumiereguy.data.local.models.AudioBookEntity
import dev.thelumiereguy.data.mapper.BookCoverToDrawableMapper
import dev.thelumiereguy.data.network.models.GetBookResponse

fun List<GetBookResponse.Data.Book>.mapResponseToEntity(bookCoverToDrawableMapper: BookCoverToDrawableMapper): List<AudioBookEntity> {
    return map { bookModel ->
        AudioBookEntity(
            bookModel.id,
            bookModel.bookName,
            bookModel.authorName,
            bookCoverToDrawableMapper.getDrawable(bookModel.bookName),
            bookModel.bookAudioUrl,
            0
        )
    }
}
