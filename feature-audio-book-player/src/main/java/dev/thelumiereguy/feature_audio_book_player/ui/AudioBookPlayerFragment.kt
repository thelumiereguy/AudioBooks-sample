package dev.thelumiereguy.feature_audio_book_player.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.os.postDelayed
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.thelumiereguy.data.models.AudioBook
import dev.thelumiereguy.feature_audio_book_player.R
import dev.thelumiereguy.feature_audio_book_player.databinding.FragmentAudioBookPlayerBinding
import dev.thelumiereguy.feature_audio_book_player.viewmodel.AudioBookPlayerViewModel
import dev.thelumiereguy.feature_audio_book_player.viewmodel.UIState

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
            showHideProgressBar(this, true)
            ivDropDown.setOnClickListener {
                popFragment()
            }

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        viewModel.updateProgress(progress)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) = Unit

                override fun onStopTrackingTouch(p0: SeekBar?) = Unit
            })
        }

        // Artificial delay
        Handler(Looper.getMainLooper()).postDelayed(500) {
            viewModel.setAudioBookDetails(
                requireArguments().getLong(BOOK_ID)
            )
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.LoadPlayerState -> {
                    setBookDetails(uiState.book)
                }
                UIState.NoSuchAudioBook -> {
                    Toast.makeText(
                        requireContext(),
                        "This Audiobook is not available at the moment. Please try again later",
                        Toast.LENGTH_SHORT
                    ).show()

                    popFragment()
                }
            }
        }
    }

    private fun setBookDetails(book: AudioBook) {
        val binding = viewBinding ?: return
        showHideProgressBar(binding, false)
        with(binding) {
            tvAuthorName.text = book.bookAuthor
            tvBookName.text = book.bookName
            tvBookTitle.text = book.bookName
            seekBar.progress = book.bookProgress
            Glide.with(requireContext())
                .load(book.bookCoverDrawable)
                .into(ivBookCover)
        }
    }

    private fun popFragment() {
        activity?.supportFragmentManager?.popBackStack()
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
        viewBinding?.seekBar?.setOnSeekBarChangeListener(null)
        viewBinding = null
        super.onDestroyView()
    }

    companion object {

        private const val BOOK_ID = "book_id"

        @JvmStatic
        fun getInstance(bookId: Long) = AudioBookPlayerFragment().apply {
            arguments = bundleOf(
                BOOK_ID to bookId
            )
            enterTransition = Slide(Gravity.BOTTOM)
            exitTransition = Slide(Gravity.BOTTOM)
        }
    }
}
