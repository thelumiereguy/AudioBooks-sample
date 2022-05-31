package dev.thelumiereguy.feature_book_listing.presentation.viewmodel

sealed interface UIEvent {
    object ShowLoading : UIEvent
    object HideLoading : UIEvent
}
