package ir.hadiagdamapps.flow.ui.screen

import android.Manifest
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.hadiagdamapps.flow.data.model.Track
import ir.hadiagdamapps.flow.ui.component.BottomControlBar
import ir.hadiagdamapps.flow.ui.component.PermissionDialog
import ir.hadiagdamapps.flow.ui.component.SongItem
import ir.hadiagdamapps.flow.ui.theme.Color
import ir.hadiagdamapps.flow.ui.theme.FlowTheme
import ir.hadiagdamapps.flow.viewmodel.SongsViewModel
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun SongsScreen(viewModel: SongsViewModel) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = viewModel::onPermissionResult
    )

    val progress = viewModel.progress.collectAsState()
    val playingSong = viewModel.playingSong.collectAsState()
    val playing = viewModel.playing.collectAsState()
    val hasPermission = viewModel.hasStoragePermission.collectAsState()


    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) { // back from settings
                viewModel.checkPermission()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    if (!hasPermission.value) {
        LaunchedEffect(Unit) {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        PermissionDialog {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
        }
    } else { // content
        Scaffold(bottomBar = {

            AnimatedVisibility(
                visible = playingSong.value != null,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {


                playingSong.value?.let {
                    BottomControlBar(
                        progress = progress.value,
                        onProgressChanged = viewModel::progressChange,
                        duration = it.duration,
                        albumUri = it.albumArtUri,
                        songTitle = it.title,
                        playing = playing.value,
                        changePlayingStatues = viewModel::changePlayingStatus,
                        next = viewModel::next,
                        previous = viewModel::prev
                    )
                }
            }
        }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.background)
                    .padding(it)
            )
            {
                Text(
                    "Songs",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )


                LazyColumn {
                    items(viewModel.tracks.value) { track: Track ->
                        SongItem(
                            albumUri = track.albumArtUri,
                            title = track.title,
                            duration = track.duration
                        ) {
                            viewModel.play(track)
                        }
                        Log.e(track.title, track.albumArtUri.toString())
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun SongsScreenPreview() {
    FlowTheme {
        val viewmodel: SongsViewModel = viewModel()

        SongsScreen(viewmodel)
    }
}