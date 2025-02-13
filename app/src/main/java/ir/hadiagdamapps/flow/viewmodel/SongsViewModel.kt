package ir.hadiagdamapps.flow.viewmodel


import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ir.hadiagdamapps.flow.data.model.Track
import ir.hadiagdamapps.flow.data.repository.SongRepository
import ir.hadiagdamapps.flow.media.MusicPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SongsViewModel(application: Application) : AndroidViewModel(application) {


    private val _tracks = MutableStateFlow<List<Track>>(listOf())
    val tracks = _tracks.asStateFlow()

    private val _hasStoragePermission = MutableStateFlow(false)
    val hasStoragePermission: StateFlow<Boolean> = _hasStoragePermission.asStateFlow()

    val playingSong: StateFlow<Track?> = MusicPlayer.currentTrack
    val progress: StateFlow<Float> = MusicPlayer.progress
    val playing: StateFlow<Boolean> = MusicPlayer.playing

    init {
        _tracks.value = SongRepository.getSongs()
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

    fun checkPermission() {
        getApplication<Application>().applicationContext.let { context ->
            (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED).let { granted ->
                if (granted && !_hasStoragePermission.value) { // re loading musics
                    SongRepository.initialize(context)
                    MusicPlayer.initialize(context)
                    _tracks.value =
                        SongRepository.getSongs().onEach { track -> Log.e("track", track.title) }
                }
                _hasStoragePermission.value = granted
            }

        }
    }

    fun onPermissionResult(granted: Boolean) {
        _hasStoragePermission.value = granted

    }

}