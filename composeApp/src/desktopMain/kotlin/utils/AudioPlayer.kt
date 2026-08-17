package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import javazoom.jl.player.Player
import org.jetbrains.compose.resources.ExperimentalResourceApi
import pomodorodesktop.composeapp.generated.resources.Res
import java.io.BufferedInputStream
import java.io.InputStream
import kotlin.concurrent.thread

class DesktopAudioPlayer(private val resourcePath: String) : AudioPlayer {
    private var player: Player? = null
    private var isLooping = false
    private var isPlaying = false
    private var inputStream: InputStream? = null

    override fun play() {
        if (isPlaying) return
        isPlaying = true
        startPlayback()
    }

    private fun startPlayback() {
        thread(start = true) {
            try {
                do {
                    inputStream = this::class.java.classLoader.getResourceAsStream(resourcePath)
                    if (inputStream == null) break
                    
                    player = Player(BufferedInputStream(inputStream))
                    player?.play()
                } while (isLooping && isPlaying)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isPlaying = false
            }
        }
    }

    override fun pause() {
        stop() // JLayer doesn't support pause easily, so we stop
    }

    override fun stop() {
        isPlaying = false
        player?.close()
        inputStream?.close()
    }

    override fun setLooping(looping: Boolean) {
        isLooping = looping
    }

    fun release() {
        stop()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun rememberAudioPlayer(fileName: String): AudioPlayer {
    // Gunakan Res.getUri() untuk mendapatkan path resource di Desktop
    val resourcePath = Res.getUri("files/$fileName")
    val player = remember { DesktopAudioPlayer(resourcePath) }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    return player
}
