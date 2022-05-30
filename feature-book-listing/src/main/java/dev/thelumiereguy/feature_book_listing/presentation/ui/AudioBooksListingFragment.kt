package dev.thelumiereguy.feature_book_listing.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import dev.thelumiereguy.feature_book_listing.R
import dev.thelumiereguy.feature_book_listing.databinding.FragmentAudioBooksListingBinding
import dev.thelumiereguy.feature_book_listing.presentation.adapter.BookListingAdapter
import dev.thelumiereguy.feature_book_listing.presentation.viewmodel.AudioBooksListingViewModel
import dev.thelumiereguy.feature_book_listing.presentation.viewmodel.UIEvent
import dev.thelumiereguy.feature_book_listing.presentation.viewmodel.UIState
import dev.thelumiereguy.helpers.ui.SimpleItemDividerDecorator
import dev.thelumiereguy.helpers.ui.toDp
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AudioBooksListingFragment : Fragment(R.layout.fragment_audio_books_listing) {

    private val viewModel: AudioBooksListingViewModel by viewModels()

    private var viewBinding: FragmentAudioBooksListingBinding? = null

    private val bookListingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BookListingAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        observeViewModel()
        viewModel.fetchBooks()
    }

    private fun setupView(view: View) {
        viewBinding = FragmentAudioBooksListingBinding.bind(view).apply {
            rvBooksList.run {
                adapter = bookListingAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    SimpleItemDividerDecorator(
                        requireContext().toDp(4).roundToInt(),
                        true
                    )
                )
            }
        }
    }

    private fun observeViewModel() {
        val binding = viewBinding ?: return

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { uiState ->
                    when (uiState) {
                        UIState.EmptyState -> {
                            // handle for empty state
                        }
                        is UIState.ListLoadedState -> {
                            bookListingAdapter.items = uiState.listItems
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { uiEvent ->
                    when (uiEvent) {
                        UIEvent.ShowLoading -> {
                            showHideProgressBar(binding, true)
                        }
                        UIEvent.HideLoading -> {
                            showHideProgressBar(binding, false)
                        }
                    }
                }
            }
        }
    }

    private fun showHideProgressBar(
        binding: FragmentAudioBooksListingBinding, showProgress: Boolean
    ) {
        TransitionManager.beginDelayedTransition(
            binding.root, Fade()
        )
        binding.flLoaderContainer.isVisible = showProgress
        binding.rvBooksList.isVisible = showProgress.not()
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}