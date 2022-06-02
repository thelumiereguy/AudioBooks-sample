package dev.thelumiereguy.feature_audio_book_player.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thelumiereguy.data.repo.BookListingRepo
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AudioBookPlayerViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val bookListingRepo: BookListingRepo,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private var bookId: Long? = null

    private val bookIdFlowFromSavedState = handle.getLiveData<Long>(BOOK_ID)

    val state = Transformations.switchMap(bookIdFlowFromSavedState) {
        this.bookId = it
        bookListingRepo.getAudioBookDetails(it)
            .asLiveData(dispatcherProvider.io)
    }.map { audioBook ->
        if (audioBook != null) {
            UIState.LoadPlayerState(audioBook)
        } else {
            UIState.NoSuchAudioBook
        }
    }

    fun setAudioBookDetails(bookId: Long) {
        handle.set(BOOK_ID, bookId)
    }

    fun updateProgress(progress: Int) {
        assert(bookId != null) {
            "Book Id would and should never be null here."
        }

        viewModelScope.launch(dispatcherProvider.main) {
            bookListingRepo.updateAudioBookProgress(bookId!!, progress)
        }
    }

    companion object {
        const val BOOK_ID = "book_id"
    }
}
