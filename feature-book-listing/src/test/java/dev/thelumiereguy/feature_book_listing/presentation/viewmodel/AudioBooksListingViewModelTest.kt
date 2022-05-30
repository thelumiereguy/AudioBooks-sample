package dev.thelumiereguy.feature_book_listing.presentation.viewmodel

import dev.thelumiereguy.ab_tests.ExperimentBucket
import dev.thelumiereguy.data.repo.fake.FakeBookListingRepoImpl
import dev.thelumiereguy.feature_book_listing.presentation.adapter.BookItem
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class AudioBooksListingViewModelTest {

    private lateinit var viewModel: AudioBooksListingViewModel

    private val dispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher = StandardTestDispatcher()
        override val default: CoroutineDispatcher = StandardTestDispatcher()
        override val io: CoroutineDispatcher = StandardTestDispatcher()
        override val unconfined: CoroutineDispatcher = StandardTestDispatcher()
    }

    init {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class OldExperiment {

        private val fakeRepo = FakeBookListingRepoImpl()

        @BeforeTest
        fun before() {
            viewModel = AudioBooksListingViewModel(
                mockk(relaxed = true),
                fakeRepo,
                dispatcherProvider,
                ExperimentBucket.B,
            )
            Dispatchers.setMain(dispatcherProvider.main)
        }

        @AfterTest
        fun after() {
            Dispatchers.resetMain()
        }

        @Test
        fun `when given a list of books,`() {
            runTest {
                val testDispatcher = UnconfinedTestDispatcher(testScheduler)
                Dispatchers.setMain(testDispatcher)

                val input = fakeRepo.fakeData
                val expectedOutput = UIState.ListLoadedState(
                    input.map(::BookItem)
                )

                viewModel.fetchBooks()

                assertEquals(UIEvent.ShowLoading, viewModel.events.first())
                assertEquals(UIEvent.HideLoading, viewModel.events.first())
                assertEquals(expectedOutput, viewModel.state.value)
            }
        }
    }
}