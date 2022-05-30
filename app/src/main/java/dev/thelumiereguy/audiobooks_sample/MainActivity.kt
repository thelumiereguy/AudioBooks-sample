package dev.thelumiereguy.audiobooks_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import dev.thelumiereguy.feature_book_listing.presentation.ui.AudioBooksListingFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        supportFragmentManager.commit {
            replace(
                R.id.fl_container,
                AudioBooksListingFragment()
            )
        }
    }
}

// ui (common)
// core (common) organization specific
// helpers (common) non-organization specific
// application
// analytics
// feature books list
// id -> feature audio player