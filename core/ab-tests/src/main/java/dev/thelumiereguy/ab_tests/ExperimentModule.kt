package dev.thelumiereguy.ab_tests

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@InstallIn(ViewModelComponent::class)
@Module
class ExperimentModule {

    @Named(BOOK_LISTING_AD)
    @Provides
    fun providesBookListingAd(): ExperimentBucket {
        return ExperimentBucket.B
    }

    companion object {
        const val BOOK_LISTING_AD = "someCampaignId"
    }
}
