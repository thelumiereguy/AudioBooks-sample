package dev.thelumiereguy.feature_audio_book_player.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import dev.thelumiereguy.data.models.AudioBook
import dev.thelumiereguy.data.repo.fake.FakeBookListingRepoImpl
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import io.mockk.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

// Added for testing livedata

@ExtendWith(InstantTaskExecutorExtension::class)
internal class AudioBookPlayerViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val uiStateObserver: Observer<UIState?> = mockk(relaxed = true)

    private lateinit var audioBookPlayerViewModel: AudioBookPlayerViewModel

    private lateinit var dispatcherProvider: DispatcherProvider

    init {
        MockKAnnotations.init(this)
    }

    private val savedStateHandle = mockk<SavedStateHandle>()

    @BeforeEach
    fun before() {
        clearMocks(uiStateObserver)
    }

    @Test
    fun `when given a bookId, set details to player state`() = runTest {

        val bookIdLiveData = MutableLiveData<Long>()

        every {
            savedStateHandle.getLiveData<Long>(AudioBookPlayerViewModel.BOOK_ID)
        } returns bookIdLiveData

        initViewmodel()

        val bookId = 1L

        audioBookPlayerViewModel.state.observeForever(uiStateObserver)

        justRun {
            savedStateHandle.set(AudioBookPlayerViewModel.BOOK_ID, bookId)
        }

        bookIdLiveData.value = bookId
        audioBookPlayerViewModel.setAudioBookDetails(bookId)

        verify {
            savedStateHandle.set(AudioBookPlayerViewModel.BOOK_ID, bookId)
        }

        advanceUntilIdle()

        verify {
            uiStateObserver.onChanged(
                UIState.LoadPlayerState(
                    AudioBook(
                        1L,
                        "The Black Witch",
                        "Laurie Forest",
                        1,
                        ""
                    )
                )
            )
        }
    }

    @Test
    fun `when given an invalid bookId, set error state`() = runTest {

        val bookIdLiveData = MutableLiveData<Long>()

        every {
            savedStateHandle.getLiveData<Long>(AudioBookPlayerViewModel.BOOK_ID)
        } returns bookIdLiveData

        initViewmodel()

        val bookId = 16L

        audioBookPlayerViewModel.state.observeForever(uiStateObserver)

        justRun {
            savedStateHandle.set(AudioBookPlayerViewModel.BOOK_ID, bookId)
        }

        bookIdLiveData.value = bookId
        audioBookPlayerViewModel.setAudioBookDetails(bookId)

        verify {
            savedStateHandle.set(AudioBookPlayerViewModel.BOOK_ID, bookId)
        }

        advanceUntilIdle()

        verify {
            uiStateObserver.onChanged(
                UIState.NoSuchAudioBook
            )
        }
    }

    private fun TestScope.initViewmodel() {

        val testDispatcher = StandardTestDispatcher(testScheduler)

        Dispatchers.setMain(testDispatcher)

        dispatcherProvider = object : DispatcherProvider {
            override val main: CoroutineDispatcher = testDispatcher
            override val default: CoroutineDispatcher = testDispatcher
            override val io: CoroutineDispatcher = testDispatcher
            override val unconfined: CoroutineDispatcher = testDispatcher
        }

        audioBookPlayerViewModel = AudioBookPlayerViewModel(
            savedStateHandle,
            FakeBookListingRepoImpl(),
            dispatcherProvider
        )
    }
}
