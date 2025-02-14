package ir.hadiagdamapps.flow.utils

import android.app.Application
import android.os.Build
import ir.hadiagdamapps.flow.data.repository.SongRepository
import ir.hadiagdamapps.flow.media.MusicPlayer
import ir.hadiagdamapps.flow.ui.MyNotification

class FlowApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SongRepository.initialize(this)
        MusicPlayer.initialize(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            MyNotification.createNotificationChannel(this)
    }

}