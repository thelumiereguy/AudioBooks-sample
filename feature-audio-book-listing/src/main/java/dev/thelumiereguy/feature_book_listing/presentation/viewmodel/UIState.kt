package dev.thelumiereguy.feature_book_listing.presentation.viewmodel

import dev.thelumiereguy.helpers.ui.adapter.BaseListItem

sealed class UIState {
    data class ListLoadedState(
        val listItems: List<BaseListItem>
    ) : UIState()

    object EmptyState : UIState()
}
