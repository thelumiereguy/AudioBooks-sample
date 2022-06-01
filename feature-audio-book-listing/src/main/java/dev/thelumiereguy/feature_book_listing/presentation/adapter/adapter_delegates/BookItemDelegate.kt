package dev.thelumiereguy.feature_book_listing.presentation.adapter.adapter_delegates

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import dev.thelumiereguy.feature_book_listing.databinding.ItemAudioBookListingBinding
import dev.thelumiereguy.feature_book_listing.presentation.adapter.BookItem
import dev.thelumiereguy.helpers.ui.adapter.BaseListItem

fun bookItemDelegate(onClick: ((bookId: Long) -> Unit)?) =
    adapterDelegateViewBinding<BookItem, BaseListItem, ItemAudioBookListingBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemAudioBookListingBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) {

        bind {
            with(binding) {
                tvBookTitle.text = item.audioBookItem.bookName
                tvBookAuthor.text = item.audioBookItem.bookAuthor
                Glide.with(context)
                    .load(item.audioBookItem.bookCoverDrawable)
                    .into(ivBookLogo)

                root.setOnClickListener {
                    onClick?.invoke(item.audioBookItem.bookId)
                }
            }
        }
    }
