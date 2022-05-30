package dev.thelumiereguy.feature_book_listing.presentation.adapter

import dev.thelumiereguy.feature_book_listing.presentation.adapter.adapter_delegates.adItemDelegate
import dev.thelumiereguy.feature_book_listing.presentation.adapter.adapter_delegates.bookItemDelegate
import dev.thelumiereguy.helpers.ui.adapter.BaseDelegateAdapter

class BookListingAdapter : BaseDelegateAdapter(
    listOf(
        bookItemDelegate(),
        adItemDelegate()
    )
)
