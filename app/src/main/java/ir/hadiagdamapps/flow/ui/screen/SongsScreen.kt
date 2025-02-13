package ir.hadiagdamapps.flow.ui.screen

import android.util.Log
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.hadiagdamapps.flow.data.model.Track
import ir.hadiagdamapps.flow.ui.component.BottomControlBar
import ir.hadiagdamapps.flow.ui.component.SongItem
import ir.hadiagdamapps.flow.ui.theme.Color
import ir.hadiagdamapps.flow.ui.theme.FlowTheme
import ir.hadiagdamapps.flow.viewmodel.SongsViewModel

@Composable
fun SongsScreen(viewModel: SongsViewModel) {

    val progress = viewModel.progress.collectAsState()
    val playingSong = viewModel.playingSong.collectAsState()
    val playing = viewModel.playing.collectAsState()

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


@Preview
@Composable
private fun SongsScreenPreview() {
    FlowTheme {
        val viewmodel: SongsViewModel = viewModel()

        SongsScreen(viewmodel)
    }
}