package dev.thelumiereguy.feature_book_listing.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thelumiereguy.ab_tests.ExperimentBucket
import dev.thelumiereguy.ab_tests.ExperimentModule
import dev.thelumiereguy.data.repo.BookListingRepo
import dev.thelumiereguy.data.models.Book
import dev.thelumiereguy.feature_book_listing.presentation.adapter.BookItem
import dev.thelumiereguy.feature_book_listing.presentation.adapter.UpgradeToBannerItem
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val books = bookListingRepo.fetchBooks()
            _events.emit(UIEvent.HideLoading)
            processResponse(books)
        }
    }

    private suspend fun processResponse(books: List<Book>) {
        if (books.isEmpty()) {
            _uiState.value = UIState.EmptyState
        }

        withContext(dispatcherProvider.default) {
            if (bookListingAdExperiment == ExperimentBucket.B) {
                mapBooksToListingItemsWithAd(books)
            } else {
                mapBooksToListingItems(books)
            }
        }
    }

    // New experiment which inserts an ad in after every 7th item
    private fun mapBooksToListingItemsWithAd(books: List<Book>) {
        if (books.size <= 7) {
            mapBooksToListingItems(books)
        } else {
            val items = books.chunked(7).flatMap {
                books.map(::BookItem) + UpgradeToBannerItem
            }
            _uiState.value = UIState.ListLoadedState(items)
        }
    }

    private fun mapBooksToListingItems(books: List<Book>) {
        val items = books.map(::BookItem)
        _uiState.value = UIState.ListLoadedState(items)
    }
}