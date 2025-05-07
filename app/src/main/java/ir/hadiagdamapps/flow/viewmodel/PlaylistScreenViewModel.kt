package ir.hadiagdamapps.flow.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import ir.hadiagdamapps.flow.data.local.AppDatabase
import ir.hadiagdamapps.flow.data.model.Track
import ir.hadiagdamapps.flow.data.repository.PlaylistRepository
import ir.hadiagdamapps.flow.data.repository.SongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaylistScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val playlistId = 0L // TODO get this from navigation

    private val playlistRepository =
        AppDatabase.getDatabase(application).playListDao().let { PlaylistRepository(it) }

    private val _tracks = MutableStateFlow<List<Track>>(listOf())
    val tracks = _tracks.asStateFlow()

    // id of selected tracks
    private val _selectedTracks = MutableStateFlow<ArrayList<String>>(arrayListOf())
    val selectedTracks = _selectedTracks.asStateFlow()

    private val _playlistTitle = MutableStateFlow<String>("")
    val playlistTitle = _playlistTitle.asStateFlow()

    private val _showDeleteDialog = MutableStateFlow<Boolean>(false)
    val showDeleteDialog = _showDeleteDialog.asStateFlow()

    private var newPlaylistTitle: String? = null
    private val handler = Handler(Looper.getMainLooper())
    private val updateTitleRunnable = kotlinx.coroutines.Runnable {
        CoroutineScope(Dispatchers.IO).launch {
            newPlaylistTitle?.let { playlistRepository.updatePlaylistTitle(playlistId, it) }
        }
    }


    init {
        _tracks.value = SongRepository.getSongs()
    }


    fun deletePlaylistClick() {
        _showDeleteDialog.value = true
    }

    fun trackRadioClick(trackId: String) {
        if (_selectedTracks.value.contains(trackId))
            _selectedTracks.value.remove(trackId)
        else
            _selectedTracks.value.add(trackId)
        CoroutineScope(Dispatchers.IO).launch {
            playlistRepository.delete(playlistId)
        }
    }

    fun setPlaylistTitle(text: String) {
        _playlistTitle.value = text
        handler.postDelayed(updateTitleRunnable, 1000)
    }

    fun deleteDialogConfirm() {
        CoroutineScope(Dispatchers.IO).launch {
            playlistRepository.delete(playlistId)
        }

        // TODO navigate back
    }

    fun deleteDialogDeny() {
        _showDeleteDialog.value = false
    }


}