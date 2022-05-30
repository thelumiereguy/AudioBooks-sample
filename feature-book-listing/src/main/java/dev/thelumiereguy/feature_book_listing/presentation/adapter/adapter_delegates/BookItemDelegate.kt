package dev.thelumiereguy.feature_book_listing.presentation.adapter.adapter_delegates

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import dev.thelumiereguy.feature_book_listing.databinding.ItemAudioBookListingBinding
import dev.thelumiereguy.feature_book_listing.presentation.adapter.BookItem

fun bookItemDelegate() =
    adapterDelegateViewBinding<BookItem, dev.thelumiereguy.helpers.ui.adapter.BaseListItem, ItemAudioBookListingBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemAudioBookListingBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) {

        bind {
            binding.run {
                tvBookTitle.text = item.bookItem.bookName
                tvBookAuthor.text = item.bookItem.bookAuthor
                Glide.with(context)
                    .load(item.bookItem.bookCoverDrawable)
                    .into(ivBookLogo)
            }
        }
    }

