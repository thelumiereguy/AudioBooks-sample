package dev.thelumiereguy.feature_book_listing.presentation.adapter.adapter_delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import dev.thelumiereguy.feature_book_listing.databinding.ItemUpgradeBannerBinding
import dev.thelumiereguy.feature_book_listing.presentation.adapter.UpgradeToBannerItem

fun upgradeBannerDelegate() =
    adapterDelegateViewBinding<UpgradeToBannerItem, dev.thelumiereguy.helpers.ui.adapter.BaseListItem, ItemUpgradeBannerBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemUpgradeBannerBinding.inflate(
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
