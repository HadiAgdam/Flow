package ir.hadiagdamapps.flow.utils

import android.app.Application
import ir.hadiagdamapps.flow.data.repository.SongRepository
import ir.hadiagdamapps.flow.media.MusicPlayer

class FlowApp: Application() {


    override fun onCreate() {
        super.onCreate()
        SongRepository.initialize(this)
        MusicPlayer.initialize(this)
    }

}