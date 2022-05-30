package dev.thelumiereguy.feature_book_listing.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.thelumiereguy.feature_book_listing.presentation.viewmodel.AudioBooksListingViewModel
import dev.thelumiereguy.feature_book_listing.R
import dev.thelumiereguy.feature_book_listing.databinding.FragmentAudioBooksListingBinding

@AndroidEntryPoint
class AudioBooksListingFragment : Fragment(R.layout.fragment_audio_books_listing) {

    private val viewModel: AudioBooksListingViewModel by viewModels()

    private var viewBinding: FragmentAudioBooksListingBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAudioBooksListingBinding.bind(view).apply {
//            rvBooksList.bind()
        }
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}