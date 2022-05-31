package dev.thelumiereguy.feature_book_listing.presentation.viewmodel

sealed class AudioBookListingActions {
    object ObserveContents : AudioBookListingActions()
    object Fetch : AudioBookListingActions()
    data class UpdateQuery(val query: CharSequence?) : AudioBookListingActions()
}
