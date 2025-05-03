package ir.hadiagdamapps.flow.viewmodel


import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ir.hadiagdamapps.flow.data.local.AppDatabase
import ir.hadiagdamapps.flow.data.model.OrderMode
import ir.hadiagdamapps.flow.data.model.Track
import ir.hadiagdamapps.flow.data.repository.PlaylistRepository
import ir.hadiagdamapps.flow.data.repository.SongRepository
import ir.hadiagdamapps.flow.media.MusicPlayer
import ir.hadiagdamapps.flow.service.MusicService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SongsViewModel(application: Application) : AndroidViewModel(application) {

    private val playlistRepository =
        AppDatabase.getDatabase(application).playListDao().let { PlaylistRepository(it) }

    val playlists = playlistRepository.playlists

    private val _orderMode = MutableStateFlow(OrderMode.TITLE)
    val orderMode = _orderMode.asStateFlow()

    private val _showOrderMenu = MutableStateFlow(false)
    val showOrderMenu = _showOrderMenu.asStateFlow()

    private val _tracks = MutableStateFlow<List<Track>>(listOf())
    val tracks = _tracks.asStateFlow()

    private val _hasStoragePermission = MutableStateFlow(false)
    val hasStoragePermission: StateFlow<Boolean> = _hasStoragePermission.asStateFlow()

    val playingSong: StateFlow<Track?> = MusicPlayer.currentTrack
    val progress: StateFlow<Float> = MusicPlayer.progress
    val playing: StateFlow<Boolean> = MusicPlayer.playing

    init {
        _tracks.value = SongRepository.getSongs()
//        CoroutineScope(Dispatchers.IO).launch {
//            playlists.onEach { list -> list.forEach { playlistRepository.delete(it.playListId) } }
//        }
    }

    fun play(track: Track) {
        getApplication<Application>().applicationContext.let {
            val intent =
                Intent(getApplication<Application>().applicationContext, MusicService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                it.startForegroundService(intent)
            else
                it.startService(intent)
        }
        _tracks.value.first { it.id == track.id }.playCount++
        MusicPlayer.play(track)
    }

    fun next() = MusicPlayer.next()

    fun prev() = MusicPlayer.prev()

    fun progressChange(progress: Float) {
        MusicPlayer.progressChange(progress)
    }

    fun changePlayingStatus() = MusicPlayer.playPause()


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

    fun showOrderMenu() {
        _showOrderMenu.value = true
    }

    fun dismissOrderMenu() {
        _showOrderMenu.value = false
    }

    fun changeOrder(mode: OrderMode) {
        _showOrderMenu.value = false
        _orderMode.value = mode
    }

    fun playlistClick(playlistId: Long) {

    }

    fun playlistEdit(playlistId: Long) {

    }

}