package dev.thelumiereguy.data.mapper

import dev.thelumiereguy.data.R
import javax.inject.Inject

/**
 * Should not really be in this module
 * Only for test purposes
 */
class BookCoverToDrawableMapper @Inject constructor() {

    fun getDrawable(bookName: String): Int {
        return when (bookName) {
            theBlackWitch -> R.drawable.black_witch
            aPromisedLand -> R.drawable.a_promised_land
            harryPotter -> R.drawable.harry_potter
            theKidnappersAccomplice -> R.drawable.the_kidnappers_accomplice
            lightMage -> R.drawable.light_mage
            sherlockHolmes -> R.drawable.sherlock_holmes
            wutheringHeights -> R.drawable.wuthering_heights
            cleanCode -> R.drawable.clean_code
            theMagiciansDiary -> R.drawable.the_magicians_diary
            theSecret -> R.drawable.the_secret
            else -> R.drawable.black_witch
        }
    }


    companion object {
        private const val theBlackWitch = "The Black Witch"
        private const val aPromisedLand = "A Promised Land"
        private const val harryPotter = "Harry Potter and the Prisoner of Azkaban"
        private const val theKidnappersAccomplice = "The Kidnapper's Accomplice"
        private const val lightMage = "Light Mage"
        private const val sherlockHolmes = "Sherlock Holmes"
        private const val wutheringHeights = "Wuthering Heights"
        private const val cleanCode = "Clean Code"
        private const val theMagiciansDiary = "The Magician's Diary"
        private const val theSecret = "The Secret"
    }
}