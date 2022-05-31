package dev.thelumiereguy.feature_book_listing.presentation.adapter

import dev.thelumiereguy.data.models.AudioBook
import dev.thelumiereguy.helpers.ui.adapter.BaseListItem

data class BookItem(val audioBookItem: AudioBook) : BaseListItem {
    override val itemId: String = audioBookItem.bookId.toString()
}

object UpgradeToBannerItem : BaseListItem {
    private const val UPGRADE_TO_BANNER_ITEM_ID = "upgradeToBanner"

    override val itemId: String = UPGRADE_TO_BANNER_ITEM_ID
}
