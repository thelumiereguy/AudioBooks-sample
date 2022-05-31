package dev.thelumiereguy.feature_audio_book_player.viewmodel

import dev.thelumiereguy.data.models.AudioBook

sealed class UIState {
    data class LoadPlayerState(val book: AudioBook) : UIState()
    object NoSuchAudioBook : UIState()
}
