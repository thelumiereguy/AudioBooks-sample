package dev.thelumiereguy.feature_book_listing.presentation.viewmodel

import dev.thelumiereguy.ab_tests.ExperimentBucket
import dev.thelumiereguy.data.models.AudioBook
import dev.thelumiereguy.data.repo.fake.FakeBookListingRepoImpl
import dev.thelumiereguy.feature_book_listing.presentation.adapter.BookItem
import dev.thelumiereguy.feature_book_listing.presentation.adapter.UpgradeToBannerItem
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.AfterTest
import kotlin.test.assertEquals

internal class AudioBooksListingViewModelTest {

    private lateinit var viewModel: AudioBooksListingViewModel

    private lateinit var testDispatcher: TestDispatcher

    private lateinit var dispatcherProvider: DispatcherProvider

    private val fakeRepo = FakeBookListingRepoImpl()

    init {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class OldExperiment {

        @AfterTest
        fun after() {
            Dispatchers.resetMain()
        }

        @Test
        fun `when given a list of books, set list loaded state`() = runTest {

            initDispatchers(ExperimentBucket.A)

            val fakeData = fakeRepo.getItems(10)

            fakeRepo.audiobookListFlow = flowOf(fakeData)

            val expectedOutput = UIState.ListLoadedState(
                fakeData.map(::BookItem)
            )

            viewModel.fetchBooks()

            assertEquals(UIEvent.ShowLoading, viewModel.events.first())

            assertEquals(UIEvent.HideLoading, viewModel.events.first())

            advanceUntilIdle()

            assertEquals(expectedOutput, viewModel.state.value)
        }

        @Test
        fun `when given an empty list of books, set empty state`() = runTest {

            initDispatchers(ExperimentBucket.A)

            val fakeDataFlow = flowOf(emptyList<AudioBook>())

            fakeRepo.audiobookListFlow = fakeDataFlow

            val expectedOutput = UIState.EmptyState

            viewModel.fetchBooks()

            assertEquals(UIEvent.ShowLoading, viewModel.events.first())

            assertEquals(UIEvent.HideLoading, viewModel.events.first())

            advanceUntilIdle()

            assertEquals(expectedOutput, viewModel.state.value)
        }
    }

    @Nested
    inner class NewUpgradeBannerExperiment {

        @AfterTest
        fun after() {
            fakeRepo.reset()
            Dispatchers.resetMain()
        }

        @Test
        fun `when given a list of 8 books, set list loaded state where the second last item is Upgrade Item`() =
            runTest {

                initDispatchers(ExperimentBucket.B)

                val fakeData = fakeRepo.getItems(8)

                fakeRepo.audiobookListFlow = flowOf(fakeData)

                val expectedOutput = UIState.ListLoadedState(
                    fakeData.map(::BookItem).take(7) +
                        UpgradeToBannerItem +
                        fakeData.map(::BookItem).last()
                )

                viewModel.fetchBooks()

                assertEquals(UIEvent.ShowLoading, viewModel.events.first())

                assertEquals(UIEvent.HideLoading, viewModel.events.first())

                advanceUntilIdle()

                assertEquals(expectedOutput, viewModel.state.value)
            }

        @Test
        fun `when given a list of 14 books, set list loaded state where every 7th item is Upgrade Item`() =
            runTest {

                initDispatchers(ExperimentBucket.B)

                val fakeData = fakeRepo.getItems(15)

                fakeRepo.audiobookListFlow = flowOf(fakeData)

                val firstHalfOfList = fakeData.slice(0..6).map(::BookItem)
                val secondHalfOfList = fakeData.slice(7..13).map(::BookItem)

                // Every 7th item will be an Upgrade Banner
                val expectedOutput = UIState.ListLoadedState(
                    firstHalfOfList +
                        UpgradeToBannerItem +
                        secondHalfOfList +
                        UpgradeToBannerItem +
                        BookItem(fakeData.last())
                )

                viewModel.fetchBooks()

                assertEquals(UIEvent.ShowLoading, viewModel.events.first())

                assertEquals(UIEvent.HideLoading, viewModel.events.first())

                advanceUntilIdle()

                assertEquals(expectedOutput, viewModel.state.value)
            }
    }

    private fun TestScope.initDispatchers(upgradeBannerExperiment: ExperimentBucket) {
        testDispatcher = StandardTestDispatcher(testScheduler)

        dispatcherProvider = object : DispatcherProvider {
            override val main: CoroutineDispatcher = testDispatcher
            override val default: CoroutineDispatcher = testDispatcher
            override val io: CoroutineDispatcher = testDispatcher
            override val unconfined: CoroutineDispatcher = testDispatcher
        }

        viewModel = AudioBooksListingViewModel(
            mockk(relaxed = true),
            fakeRepo,
            dispatcherProvider,
            upgradeBannerExperiment,
        )
    }
}
