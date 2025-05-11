package ir.hadiagdamapps.flow.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.hadiagdamapps.flow.viewmodel.PlaylistScreenViewModel

class PlaylistScreenViewModelFactory(private val application: Application, private val playlistId: Long): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistScreenViewModel::class.java)) {
            return PlaylistScreenViewModel(application, playlistId) as T
        }
        throw IllegalArgumentException("Unknown class")
    }
}