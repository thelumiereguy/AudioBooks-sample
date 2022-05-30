package dev.thelumiereguy.feature_book_listing.presentation.adapter

import dev.thelumiereguy.data.models.Book
import dev.thelumiereguy.helpers.ui.adapter.BaseListItem

data class BookItem(val bookItem: Book) : BaseListItem {
    override val itemId: String = bookItem.bookId.toString()
}

object UpgradeToBannerItem : BaseListItem {
    private const val UPGRADE_TO_BANNER_ITEM_ID = "upgradeToBanner"

    override val itemId: String = UPGRADE_TO_BANNER_ITEM_ID
}