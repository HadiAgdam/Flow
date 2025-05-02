package ir.hadiagdamapps.flow.media

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import ir.hadiagdamapps.flow.data.local.AppDatabase
import ir.hadiagdamapps.flow.data.model.Track
import ir.hadiagdamapps.flow.data.repository.MetaDataRepository
import ir.hadiagdamapps.flow.data.repository.SongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object MusicPlayer {

    private const val UPDATE_DELAY = 500L

    private lateinit var metaDataRepository: MetaDataRepository

    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack: StateFlow<Track?> = _currentTrack

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    private val _playing = MutableStateFlow(false)
    val playing: StateFlow<Boolean> = _playing

    private lateinit var exoPlayer: ExoPlayer
    private val handler = Handler(Looper.myLooper()!!)
    private val updateProgressRunnable = object : Runnable {
        override fun run() {
            _progress.value = exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()
            handler.postDelayed(this, UPDATE_DELAY)
        }
    }


    private val queue = ArrayList<Track>()
    private var pointer = -1

    fun initialize(context: Context) {
        metaDataRepository = AppDatabase.getDatabase(context).musicMetadataDao().let { MetaDataRepository(it) }
        SongRepository.getSongs().forEach { queue.add(it) }
        exoPlayer = ExoPlayer.Builder(context).build()
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_ENDED) {
                    next()
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                Handler(Looper.myLooper()!!).postDelayed({
                    if (exoPlayer.isPlaying == isPlaying) _playing.value = isPlaying // I don't believe it fixed
                }, 100)
            }
        })
    }

    fun play(track: Track) {
        _currentTrack.value = track
        exoPlayer.setMediaItem(MediaItem.fromUri(track.uri))
        exoPlayer.prepare()
        exoPlayer.play()
        handler.post(updateProgressRunnable)
        _progress.value = 0f
        pointer = queue.indexOf(track)
        CoroutineScope(Dispatchers.IO).launch {
            metaDataRepository.incrementPlayCount(track.id)
        }
    }

    fun resume() {
        exoPlayer.play()
        handler.post(updateProgressRunnable)
    }

    fun pause() {
        exoPlayer.pause()
        handler.removeCallbacks(updateProgressRunnable)
    }

    fun next() {
        if (pointer == queue.size - 1)
            pointer = 0
        else
            pointer++
        play(queue[pointer])
    }

    fun prev() {
        if (pointer == 0)
            pointer = queue.size - 1
        else
            pointer--
        play(queue[pointer])
    }

    fun progressChange(progress: Float) {
        exoPlayer.seekTo((progress * exoPlayer.duration).toLong())
    }



    fun playPause() {
        if (playing.value) pause() else resume()
    }
}