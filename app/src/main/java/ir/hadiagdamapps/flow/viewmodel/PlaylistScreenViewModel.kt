package ir.hadiagdamapps.flow.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import ir.hadiagdamapps.flow.data.local.AppDatabase
import ir.hadiagdamapps.flow.data.local.Playlist
import ir.hadiagdamapps.flow.data.model.Track
import ir.hadiagdamapps.flow.data.repository.PlaylistRepository
import ir.hadiagdamapps.flow.data.repository.SongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaylistScreenViewModel(private val application: Application, private val playlistId: Long) :
    AndroidViewModel(application) {


    private val playlistRepository =
        AppDatabase.getDatabase(application).playListDao().let { PlaylistRepository(it) }

    private val _tracks = MutableStateFlow<List<Track>>(listOf())
    val tracks = _tracks.asStateFlow()

    private val _selectedTracks = MutableStateFlow<List<String>>(listOf())
    val selectedTracks = _selectedTracks.asStateFlow()

    private val _playlistTitle = MutableStateFlow<String>("")
    val playlistTitle = _playlistTitle.asStateFlow()

    private val _showDeleteDialog = MutableStateFlow<Boolean>(false)
    val showDeleteDialog = _showDeleteDialog.asStateFlow()

    private var playlist: Playlist? = null

    private val handler = Handler(Looper.getMainLooper())
    private val updateTitleRunnable = kotlinx.coroutines.Runnable {
        CoroutineScope(Dispatchers.IO).launch {
            playlistRepository.updatePlaylistTitle(playlistId, _playlistTitle.value)
            playlist = playlist?.copy(title = _playlistTitle.value)
            Log.e("new playlist", _playlistTitle.value)
        }
    }

    private val _navigateBack = MutableStateFlow(false)
    var navigateBack = _navigateBack.asStateFlow()


    init {
        _tracks.value = SongRepository.getSongs()
        CoroutineScope(Dispatchers.IO).launch {
            playlist = playlistRepository.getPlaylist(playlistId)
            _playlistTitle.value = playlist!!.title
            _selectedTracks.value = playlist!!.songs.also{ Log.e("playlist songs", it)}.split(",")
        }
    }


    fun deletePlaylistClick() {
        _showDeleteDialog.value = true
    }

    fun trackRadioClick(trackId: String) {
        if (_selectedTracks.value.contains(trackId))
            _selectedTracks.value -= trackId
        else
            _selectedTracks.value += trackId

        CoroutineScope(Dispatchers.IO).launch {
            _selectedTracks.value.joinToString(",").let {
                playlistRepository.updatePlaylistSongs(playlistId, it)
                Log.e("tracks", it)
                playlist = playlist?.copy(songs = it)
            }
        }

    }

    fun setPlaylistTitle(text: String) {
        _playlistTitle.value = text
        handler.removeCallbacks(updateTitleRunnable)
        handler.postDelayed(updateTitleRunnable, 1000)
    }

    fun deleteDialogConfirm() {
        CoroutineScope(Dispatchers.IO).launch {
            playlistRepository.delete(playlistId)
        }

        _navigateBack.value = true
    }

    fun deleteDialogDeny() {
        _showDeleteDialog.value = false
    }


}