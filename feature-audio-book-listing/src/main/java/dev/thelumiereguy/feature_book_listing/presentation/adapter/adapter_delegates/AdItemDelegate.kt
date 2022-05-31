package dev.thelumiereguy.feature_book_listing.presentation.adapter.adapter_delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import dev.thelumiereguy.feature_book_listing.databinding.ItemAdBookListingBinding
import dev.thelumiereguy.feature_book_listing.presentation.adapter.UpgradeToBannerItem

fun adItemDelegate() =
    adapterDelegateViewBinding<UpgradeToBannerItem, dev.thelumiereguy.helpers.ui.adapter.BaseListItem, ItemAdBookListingBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemAdBookListingBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) {

        bind {
            binding.run {
                ivAdImage.setImageResource(dev.thelumiereguy.ui.R.drawable.ic_logo_full)
            }
        }
    }
