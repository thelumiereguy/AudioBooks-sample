package dev.thelumiereguy.audiobooks_sample

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

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
        }
    }
}
