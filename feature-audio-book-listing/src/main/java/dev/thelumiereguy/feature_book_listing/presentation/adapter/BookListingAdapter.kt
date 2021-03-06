package dev.thelumiereguy.feature_book_listing.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import dev.thelumiereguy.feature_book_listing.presentation.adapter.adapter_delegates.bookItemDelegate
import dev.thelumiereguy.feature_book_listing.presentation.adapter.adapter_delegates.upgradeBannerDelegate
import dev.thelumiereguy.helpers.ui.adapter.BaseDelegateAdapter

class BookListingAdapter(private var onClick: ((bookId: Long) -> Unit)?) : BaseDelegateAdapter(
    listOf(
        bookItemDelegate(onClick),
        upgradeBannerDelegate()
    )
) {
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        onClick = null
    }
}
