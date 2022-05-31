package dev.thelumiereguy.feature_book_listing.presentation.adapter.adapter_delegates

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import dev.thelumiereguy.feature_book_listing.databinding.ItemAudioBookListingBinding
import dev.thelumiereguy.feature_book_listing.presentation.adapter.BookItem

fun bookItemDelegate(onClick: ((bookId: Long) -> Unit)?) =
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
                tvBookTitle.text = item.audioBookItem.bookName
                tvBookAuthor.text = item.audioBookItem.bookAuthor
                Glide.with(context)
                    .load(item.audioBookItem.bookCoverDrawable)
                    .into(ivBookLogo)

                root.setOnClickListener {
                    root.requestFocus()
                    onClick?.invoke(item.audioBookItem.bookId)
                }
            }
        }
    }
