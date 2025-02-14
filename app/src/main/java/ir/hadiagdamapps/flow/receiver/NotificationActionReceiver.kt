package ir.hadiagdamapps.flow.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ir.hadiagdamapps.flow.media.MusicPlayer
import ir.hadiagdamapps.flow.utils.MusicAction

class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {

            MusicAction.PLAY_PAUSE -> MusicPlayer.playPause()

            MusicAction.SKIP_NEXT -> MusicPlayer.next()

            MusicAction.SKIP_PREVIOUS -> MusicPlayer.prev()

        }
    }
}