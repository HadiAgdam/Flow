# Flow - Music Player App

Flow is a music player app designed with Jetpack Compose, ViewModel, and a centralized `MusicPlayer` object for efficient state management. It provides reliable music playback while updating the UI dynamically and integrates deeply with background services, notifications, and broadcast receivers for seamless user experience.

## Features (Version 1)

- **Centralized Music Player State Management**  
  The app uses a `MusicPlayer` object to manage playback states, such as the current track, progress, and play/pause status. These states are exposed through Kotlin's `StateFlow`, ensuring that the UI always reflects the latest state.

- **Background Service**  
  Flow employs a background service to keep the music playing even when the app is closed. The service is responsible for updating the playback state and ensuring the app runs continuously in the background.

- **Notification Integration**  
  Flow integrates a persistent notification to show current track details, playback progress, and play/pause controls. The notification is dynamically updated based on the app’s playback state.

- **BroadcastReceiver Integration**  
  The app uses a `BroadcastReceiver` to listen for external events like play, pause, skip next, and skip previous actions. These actions trigger corresponding functions in the `MusicPlayer` object, keeping the app responsive to external input.

## Architecture

The app's core architecture revolves around the `MusicPlayer` object and integrates with Jetpack Compose for UI and ViewModel for state management.

- **MusicPlayer Object**  
  The `MusicPlayer` object holds key states such as the current track (`currentTrack`), progress (`progress`), and play/pause status (`playing`). These states are updated in real-time using Kotlin's `StateFlow` and are observed by the UI for automatic updates.

  ```kotlin
  private val _currentTrack = MutableStateFlow<Track?>(null)
  val currentTrack: StateFlow<Track?> = _currentTrack
  
  private val _progress = MutableStateFlow(0f)
  val progress: StateFlow<Float> = _progress
  
  private val _playing = MutableStateFlow(false)
  val playing: StateFlow<Boolean> = _playing
  ```

- **ExoPlayer Integration**  
  ExoPlayer is used for audio playback, and the progress is regularly updated using a `Handler` and `Runnable`.

  ```kotlin
  private lateinit var exoPlayer: ExoPlayer
  private val handler = Handler(Looper.myLooper()!!)
  private val updateProgressRunnable = object : Runnable {
      override fun run() {
          _progress.value = exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()
          handler.postDelayed(this, UPDATE_DELAY)
      }
  }
  ```

- **Service & BroadcastReceiver**  
  The service ensures playback continues in the background, while a `BroadcastReceiver` listens for play/pause and skip actions from external sources (like Bluetooth or media buttons).

  Example of `BroadcastReceiver`:

  ```kotlin
  class NotificationActionReceiver : BroadcastReceiver() {
      override fun onReceive(context: Context, intent: Intent) {
          when (intent.action) {
              MusicAction.PLAY_PAUSE -> MusicPlayer.playPause()
              MusicAction.SKIP_NEXT -> MusicPlayer.next()
              MusicAction.SKIP_PREVIOUS -> MusicPlayer.prev()
          }
      }
  }
  ```

- **Updating Notification with Playback State**  
  The app updates the notification in real-time by collecting states from the `MusicPlayer` object.

  ```kotlin
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
  ```

## Planned Features (Future Versions)

- **Version 2**  
  - **Shuffle**: Add shuffle functionality to randomize the track order.
  - **Change Display Order**: Ability to change the order of displayed tracks.
  - **Playing Music Screen**: A dedicated screen for music playback with album art, track details, and a progress bar.
  - **Action Support**: Enable control via external actions such as Bluetooth media buttons or phone call interruptions.

- **Version 3**  
  - **Like/Dislike**: Add the ability to like or dislike a song.
  - **Delete a Song**: Allow users to delete songs from their library.
  - **Most Played**: Track and display the most played songs.
  - **Search**: Implement a search function to easily find songs.
