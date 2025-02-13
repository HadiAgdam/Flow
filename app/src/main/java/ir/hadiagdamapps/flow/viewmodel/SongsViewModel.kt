package ir.hadiagdamapps.flow.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import ir.hadiagdamapps.flow.data.model.Track
import ir.hadiagdamapps.flow.data.repository.SongRepository
import ir.hadiagdamapps.flow.media.MusicPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SongsViewModel: ViewModel() {

    private val _tracks = MutableStateFlow<List<Track>>(listOf())
    val tracks = _tracks.asStateFlow()

    val playingSong: StateFlow<Track?> = MusicPlayer.currentTrack
    val progress: StateFlow<Float> = MusicPlayer.progress
    val playing: StateFlow<Boolean> = MusicPlayer.playing

    init {
        _tracks.value = SongRepository.getSongs().onEach { Log.e("song loaded", it.title) }
    }

    fun play(track: Track) = MusicPlayer.play(track)

    fun next() = MusicPlayer.next()

    fun prev() = MusicPlayer.prev()

    fun progressChange(progress: Float) {
        MusicPlayer.progressChange(progress)
    }

    fun changePlayingStatus() {
        if (playing.value) MusicPlayer.pause() else MusicPlayer.resume()
    }

}