package ir.hadiagdamapps.flow.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import ir.hadiagdamapps.flow.media.MusicPlayer
import ir.hadiagdamapps.flow.ui.MyNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

class MusicService : Service() {

    private val notificationId = 1
    private val scope = CoroutineScope(Dispatchers.Main)
    private var initialized = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (initialized) return START_STICKY

        initialized = true

        scope.launch {

            combine(
                MusicPlayer.playing,
                MusicPlayer.currentTrack,
                MusicPlayer.progress
            ) { playing, currentTrack, progress ->
                Triple(playing, currentTrack, progress)
            }.collect {

                val notification = MyNotification.buildMusicNotification(
                    applicationContext
                )
                getSystemService(NotificationManager::class.java).notify(notificationId, notification)
            }
        }


        val notification = MyNotification.buildMusicNotification(
            applicationContext
        )
        startForeground(notificationId, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? = null
}