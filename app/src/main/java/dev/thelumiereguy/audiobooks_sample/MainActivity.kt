package dev.thelumiereguy.audiobooks_sample

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import dev.thelumiereguy.feature_audio_book_player.ui.AudioBookPlayerFragment
import dev.thelumiereguy.feature_book_listing.presentation.ui.AudioBookListingUIActions
import dev.thelumiereguy.feature_book_listing.presentation.ui.AudioBooksListingFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), AudioBookListingUIActions {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(
                    R.id.fl_container,
                    AudioBooksListingFragment()
                )
            }
        }
    }

    /**
     * Added special handling for Android Q
     * see [https://issuetracker.google.com/issues/139738913]
     */
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && !isTaskRoot) {
            super.onBackPressed()
            return
        }

        if (supportFragmentManager.backStackEntryCount == 0) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

    override fun navigateToPlayer(bookId: Long) {
        supportFragmentManager.commit {
            addToBackStack(AudioBooksListingFragment::class.java.name)
            add(
                R.id.fl_container,
                AudioBookPlayerFragment.getInstance(bookId)
            )
        }
    }
}
