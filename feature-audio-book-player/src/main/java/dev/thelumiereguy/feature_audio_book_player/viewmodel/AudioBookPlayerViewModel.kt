package dev.thelumiereguy.feature_audio_book_player.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thelumiereguy.data.repo.BookListingRepo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioBookPlayerViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val bookListingRepo: BookListingRepo
) : ViewModel() {

    private var bookId: Long? = null

    private val bookIdLiveData = handle.getLiveData<Long>(BOOK_ID).asFlow()

    val state = bookIdLiveData.flatMapLatest { bookId ->
        this.bookId = bookId
        bookListingRepo.getAudioBookDetails(bookId)
    }.map { audioBook ->
        if (audioBook != null) {
            UIState.LoadPlayerState(audioBook)
        } else {
            UIState.NoSuchAudioBook
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
        .filterNotNull()

    fun setAudioBookDetails(bookId: Long) {
        handle.set(BOOK_ID, bookId)
    }

    fun updateProgress(progress: Int) {
        assert(bookId != null) {
            "Book Id would and should never be null here."
        }

        viewModelScope.launch {
            bookListingRepo.updateAudioBookProgress(bookId!!, progress)
        }
    }

    companion object {
        private const val BOOK_ID = "book_id"
    }
}
