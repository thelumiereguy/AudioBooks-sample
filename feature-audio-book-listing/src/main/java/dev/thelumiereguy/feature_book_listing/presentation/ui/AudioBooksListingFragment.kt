package dev.thelumiereguy.feature_book_listing.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
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
import dev.thelumiereguy.feature_book_listing.presentation.viewmodel.AudioBookListingActions
import dev.thelumiereguy.feature_book_listing.presentation.viewmodel.AudioBooksListingViewModel
import dev.thelumiereguy.feature_book_listing.presentation.viewmodel.UIEvent
import dev.thelumiereguy.feature_book_listing.presentation.viewmodel.UIState
import dev.thelumiereguy.helpers.ui.SimpleItemDividerDecorator
import dev.thelumiereguy.helpers.ui.toDp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class AudioBooksListingFragment : Fragment(R.layout.fragment_audio_books_listing) {

    private val viewModel: AudioBooksListingViewModel by viewModels()

    private var viewBinding: FragmentAudioBooksListingBinding? = null

    private val searchTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.add(AudioBookListingActions.UpdateQuery(p0))
        }

        override fun afterTextChanged(p0: Editable?) = Unit
    }

    private val bookListingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BookListingAdapter(
            onClick = { bookId ->
                navigateToPlayer(bookId)
            }
        )
    }

    private fun navigateToPlayer(bookId: Long) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        observeViewModel()

        viewModel.add(AudioBookListingActions.ObserveContents)

        viewModel.add(AudioBookListingActions.Fetch)
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
                            showAfterFadeTransition(binding) {
                                flLoaderContainer.isVisible = false
                                rvBooksList.isVisible = false
                                tvNoItems.isVisible = true
                            }
                        }
                        is UIState.ListLoadedState -> {
                            binding.tvNoItems.isVisible = false
                            binding.rvBooksList.isVisible = true
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
                            showAfterFadeTransition(binding) {
                                flLoaderContainer.isVisible = true
                                rvBooksList.isVisible = false
                            }
                        }
                        UIEvent.HideLoading -> {
                            showAfterFadeTransition(binding) {
                                flLoaderContainer.isVisible = false
                                rvBooksList.isVisible = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showAfterFadeTransition(
        binding: FragmentAudioBooksListingBinding,
        block: FragmentAudioBooksListingBinding.() -> Unit
    ) {
        TransitionManager.beginDelayedTransition(
            binding.root as ViewGroup, Fade()
        )
        block(binding)
    }

    override fun onPause() {
        super.onPause()
        viewBinding?.etSearchField?.removeTextChangedListener(searchTextChangedListener)
    }

    override fun onResume() {
        super.onResume()
        viewBinding?.etSearchField?.addTextChangedListener(searchTextChangedListener)
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}
