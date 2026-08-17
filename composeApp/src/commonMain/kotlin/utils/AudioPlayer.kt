package utils

import androidx.compose.runtime.Composable

interface AudioPlayer {
    fun play()
    fun pause()
    fun stop()
    fun setLooping(looping: Boolean)
}

@Composable
expect fun rememberAudioPlayer(fileName: String): AudioPlayer