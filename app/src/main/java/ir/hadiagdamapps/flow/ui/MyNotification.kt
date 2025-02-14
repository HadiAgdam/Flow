package ir.hadiagdamapps.flow.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ir.hadiagdamapps.flow.R
import ir.hadiagdamapps.flow.media.MusicPlayer
import ir.hadiagdamapps.flow.receiver.NotificationActionReceiver
import ir.hadiagdamapps.flow.utils.MusicAction
import ir.hadiagdamapps.flow.utils.toMinutes

object MyNotification {

    private const val CHANNEL_ID = "channel"


    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context) {
        val channel =
            NotificationChannel(CHANNEL_ID, "Music Player", NotificationManager.IMPORTANCE_LOW)
        context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    fun buildMusicNotification(
        context: Context,
    ): Notification {

        val track = MusicPlayer.currentTrack.value!!

        RemoteViews(context.packageName, R.layout.music_notification_layout).let { view ->

            view.setOnClickPendingIntent( // play pause
                R.id.pausePlayIcon,
                PendingIntent.getBroadcast(
                    context,
                    0,
                    Intent(context, NotificationActionReceiver::class.java).apply {
                        action = MusicAction.PLAY_PAUSE
                    },
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            view.setImageViewResource(
                R.id.pausePlayIcon,
                if (MusicPlayer.playing.value) R.drawable.pause_icon else R.drawable.play_icon
            )


            view.setOnClickPendingIntent( // next
                R.id.nextIcon,
                PendingIntent.getBroadcast(
                    context, 0,
                    Intent(context, NotificationActionReceiver::class.java).apply {
                        action = MusicAction.SKIP_NEXT
                    },
                    PendingIntent.FLAG_IMMUTABLE
                )
            )


            view.setOnClickPendingIntent( // prev
                R.id.previousIcon,
                PendingIntent.getBroadcast(
                    context, 0,
                    Intent(context, NotificationActionReceiver::class.java).apply {
                        action = MusicAction.SKIP_PREVIOUS
                    },
                    PendingIntent.FLAG_IMMUTABLE
                )
            )

            view.setTextViewText( // progress text
                R.id.progressText,
                (MusicPlayer.currentTrack.value!!.duration * MusicPlayer.progress.value).toLong().toMinutes()
            )

            view.setTextViewText( // duration text
                R.id.durationText,
                MusicPlayer.currentTrack.value!!.duration.toMinutes()
            )

            view.setProgressBar( // progress bar
                R.id.progressBar,
                100,
                (MusicPlayer.progress.value * 100).toInt(),
                false
            )

            view.setTextViewText( // title
                R.id.titleText,
                track.title
            )

            track.albumArtUri?.let {
                view.setImageViewUri(R.id.image, Uri.parse(it))
            } ?: {
                view.setImageViewResource(R.id.image, R.drawable.music_icon)
            }


            return NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.music_icon)
                .setCustomContentView(view)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .build()
        }

    }


}