package dev.thelumiereguy.data.repo.fake

import dev.thelumiereguy.data.models.AudioBook
import dev.thelumiereguy.data.repo.BookListingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FakeBookListingRepoImpl : BookListingRepo {

    companion object {
        private val books = listOf(
            "The Black Witch" to "Laurie Forest",
            "A Promised Land" to "Barrack Obama",
            "Harry Potter and the Prisoner of Azkaban" to "J.K. Rowling",
            "The Kidnapper's Accomplice" to "C.J. Archer",
            "Light Mage" to "Laurie Forest",
            "Sherlock Holmes" to "Sir Arthur Conan Doyle",
            "Wuthering Heights" to "Emily Brontë",
            "Clean Code" to "Robert C. Martin",
            "The Magician's Diary" to "C.J. Archer",
            "The Secret" to "Rhonda Byrne",
        )
    }

    fun getItems(itemCount: Int) = List(itemCount) { index ->
        val book = books[index % books.size]
        AudioBook(
            index.toLong() + 1,
            book.first,
            book.second,
            index + 1,
            "",
        )
    }

    var audiobookListFlow = flowOf(getItems(10))

    override fun observeAudioBooks(): Flow<List<AudioBook>> {
        return audiobookListFlow
    }

    override fun getAudioBookDetails(bookId: Long): Flow<AudioBook?> {
        return flowOf(getItems(10).find { it.bookId == bookId })
    }

    override suspend fun refreshAudioBooks() = Unit

    override fun searchAudioBooks(searchString: String): Flow<List<AudioBook>?> {
        return audiobookListFlow.map {
            it.filter { audioBook ->
                audioBook.bookAuthor.contains(searchString) ||
                    audioBook.bookName.contains(searchString)
            }
        }
    }

    override suspend fun updateAudioBookProgress(bookId: Long, progress: Int) {
        audiobookListFlow = flowOf(
            getItems(10).map {
                if (it.bookId == bookId) {
                    it.copy(
                        bookProgress = progress
                    )
                } else {
                    it
                }
            }
        )
    }

    fun reset() {
        audiobookListFlow = flowOf(getItems(10))
    }
}
