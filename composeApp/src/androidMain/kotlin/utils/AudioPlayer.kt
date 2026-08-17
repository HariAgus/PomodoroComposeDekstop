package utils

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import pomodorodesktop.composeapp.generated.resources.Res
import java.io.File
import java.io.FileOutputStream

class AndroidAudioPlayer(private val mediaPlayer: MediaPlayer) : AudioPlayer {
    private var isPrepared = false
    private var shouldPlayWhenReady = false

    init {
        mediaPlayer.setOnPreparedListener {
            Log.d("AudioPlayer", "MediaPlayer SIAP")
            isPrepared = true
            if (shouldPlayWhenReady) {
                mediaPlayer.start()
                shouldPlayWhenReady = false
            }
        }
        mediaPlayer.setOnErrorListener { _, what, extra ->
            Log.e("AudioPlayer", "MediaPlayer ERROR: what=$what, extra=$extra")
            isPrepared = false
            shouldPlayWhenReady = false
            true
        }
    }

    override fun play() {
        if (isPrepared) {
            mediaPlayer.start()
        } else {
            Log.d("AudioPlayer", "Menunggu file siap diputar...")
            shouldPlayWhenReady = true
        }
    }

    override fun pause() {
        shouldPlayWhenReady = false
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun stop() {
        shouldPlayWhenReady = false
        if (isPrepared) {
            mediaPlayer.stop()
            mediaPlayer.prepareAsync()
            isPrepared = false
        }
    }

    override fun setLooping(looping: Boolean) {
        mediaPlayer.isLooping = looping
    }

    fun release() {
        mediaPlayer.release()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun rememberAudioPlayer(fileName: String): AudioPlayer {
    val context = LocalContext.current
    val mediaPlayer = remember {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
    }

    val audioPlayer = remember(mediaPlayer) { AndroidAudioPlayer(mediaPlayer) }

    // Proses copy file ke cache secara asynchronous
    LaunchedEffect(fileName) {
        try {
            val cacheFile = File(context.cacheDir, fileName)
            if (!cacheFile.exists()) {
                Log.d("AudioPlayer", "Menyalin file ke cache...")
                val bytes = Res.readBytes("files/$fileName")
                FileOutputStream(cacheFile).use { it.write(bytes) }
                Log.d("AudioPlayer", "Penyalinan selesai")
            }
            
            mediaPlayer.reset()
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            mediaPlayer.setDataSource(cacheFile.absolutePath)
            mediaPlayer.setVolume(1.0f, 1.0f)
            mediaPlayer.prepareAsync()
            Log.d("AudioPlayer", "File cache siap: ${cacheFile.absolutePath}")
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Gagal menyiapkan file audio: ${e.message}")
            e.printStackTrace()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            audioPlayer.release()
        }
    }

    return audioPlayer
}
