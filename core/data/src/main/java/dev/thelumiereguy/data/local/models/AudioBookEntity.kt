package dev.thelumiereguy.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "audio_books"
)
data class AudioBookEntity(
    @PrimaryKey val book_id: Long,
    @ColumnInfo(name = "book_name") val bookName: String,
    @ColumnInfo(name = "book_author") val bookAuthor: String,
    @ColumnInfo(name = "book_cover_drawable_id") val bookCoverDrawable: Int,
    @ColumnInfo(name = "book_audio_url") val bookAudioUrl: String,
    @ColumnInfo(name = "book_progress") val progress: Int
)
