package dev.thelumiereguy.feature_audio_book_player.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.Fade
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import dev.thelumiereguy.feature_audio_book_player.R
import dev.thelumiereguy.feature_audio_book_player.databinding.FragmentAudioBookPlayerBinding
import dev.thelumiereguy.feature_audio_book_player.viewmodel.AudioBookPlayerViewModel

@AndroidEntryPoint
class AudioBookPlayerFragment : Fragment(R.layout.fragment_audio_book_player) {

    private val viewModel: AudioBookPlayerViewModel by viewModels()

    private var viewBinding: FragmentAudioBookPlayerBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        observeViewModel()
    }

    private fun setupView(view: View) {
        viewBinding = FragmentAudioBookPlayerBinding.bind(view).apply {
        }
    }

    private fun observeViewModel() {
        val binding = viewBinding ?: return

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
//                viewModel.state.collect { uiState ->
//                    when (uiState) {
//                        UIState.EmptyState -> {
//                            // handle for empty state
//                        }
//                        is UIState.ListLoadedState -> {
//                            bookListingAdapter.items = uiState.listItems
//                        }
//                    }
//                }
//            }
//        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.events.collect { uiEvent ->
//                    when (uiEvent) {
//                        UIEvent.ShowLoading -> {
//                            showHideProgressBar(binding, true)
//                        }
//                        UIEvent.HideLoading -> {
//                            showHideProgressBar(binding, false)
//                        }
//                    }
//                }
//            }
//        }
    }

    private fun showHideProgressBar(
        binding: FragmentAudioBookPlayerBinding,
        showProgress: Boolean
    ) {
        TransitionManager.beginDelayedTransition(
            binding.root, Fade()
        )
        binding.progressCircular.isVisible = showProgress
        binding.groupContent.isVisible = showProgress.not()
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}
