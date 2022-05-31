package dev.thelumiereguy.feature_book_listing.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thelumiereguy.ab_tests.ExperimentBucket
import dev.thelumiereguy.ab_tests.ExperimentModule
import dev.thelumiereguy.data.models.AudioBook
import dev.thelumiereguy.data.repo.BookListingRepo
import dev.thelumiereguy.feature_book_listing.presentation.adapter.BookItem
import dev.thelumiereguy.feature_book_listing.presentation.adapter.UpgradeToBannerItem
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import dev.thelumiereguy.helpers.ui.adapter.BaseListItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AudioBooksListingViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val bookListingRepo: BookListingRepo,
    private val dispatcherProvider: DispatcherProvider,
    @Named(ExperimentModule.BOOK_LISTING_AD) private val bookListingAdExperiment: ExperimentBucket
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.ListLoadedState(emptyList()))

    val state = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UIEvent>()

    val events = _events.asSharedFlow()

    fun fetchBooks() {
        viewModelScope.launch(dispatcherProvider.main) {
            _events.emit(UIEvent.ShowLoading)
            bookListingRepo.refreshAudioBooks()
        }

        viewModelScope.launch(dispatcherProvider.main) {
            bookListingRepo.observeAudioBooks()
                .distinctUntilChanged()
                .collectLatest { audioBooks ->
                    _events.emit(UIEvent.HideLoading)

                    if (audioBooks.isNullOrEmpty()) {
                        _uiState.value = UIState.EmptyState
                        return@collectLatest
                    }

                    processResponse(audioBooks)
                }
        }
    }

    private suspend fun processResponse(audioBooks: List<AudioBook>) {
        withContext(dispatcherProvider.default) {
            if (bookListingAdExperiment == ExperimentBucket.B) {
                mapBooksToListingItemsWithAd(audioBooks)
            } else {
                mapBooksToListingItems(audioBooks)
            }
        }
    }

    // New experiment which inserts an ad in after every 7th item
    private fun mapBooksToListingItemsWithAd(audioBooks: List<AudioBook>) {
        if (audioBooks.size <= upgradeBannerPosition) {
            mapBooksToListingItems(audioBooks)
        } else {
            val chunkedListOfBooks: List<List<BaseListItem>> = audioBooks
                .map(::BookItem)
                .chunked(upgradeBannerPosition)

            val items = chunkedListOfBooks.reduce { acc, chunkOfBooks ->
                acc + listOf(UpgradeToBannerItem) + chunkOfBooks
            }

            _uiState.value = UIState.ListLoadedState(items)
        }
    }

    private fun mapBooksToListingItems(audioBooks: List<AudioBook>) {
        val items = audioBooks.map(::BookItem)
        _uiState.value = UIState.ListLoadedState(items)
    }

    companion object {
        private const val upgradeBannerPosition = 7
    }
}