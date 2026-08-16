package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFoundation.AVAudioPlayer
import platform.Foundation.NSURL
import pomodorodesktop.composeapp.generated.resources.Res

class IosAudioPlayer(private val player: AVAudioPlayer) : AudioPlayer {
    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun stop() {
        player.stop()
        player.setCurrentTime(0.0)
    }

    override fun setLooping(looping: Boolean) {
        player.numberOfLoops = if (looping) -1L else 0L
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun rememberAudioPlayer(fileName: String): AudioPlayer {
    val player = remember {
        val uriString = Res.getUri("files/$fileName")
        val url = NSURL.URLWithString(uriString)!!
        AVAudioPlayer(uRL = url, error = null).apply {
            prepareToPlay()
        }
    }

    val audioPlayer = remember(player) { IosAudioPlayer(player) }

    DisposableEffect(Unit) {
        onDispose {
            // AVAudioPlayer doesn't need explicit release like MediaPlayer
        }
    }

    return audioPlayer
}
