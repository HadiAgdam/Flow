package ir.hadiagdamapps.flow.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.hadiagdamapps.flow.ui.component.ConfirmDeletePlaylistDialog
import ir.hadiagdamapps.flow.ui.component.PlaylistSongItem
import ir.hadiagdamapps.flow.ui.theme.FlowTheme
import ir.hadiagdamapps.flow.viewmodel.PlaylistScreenViewModel

@Composable
fun PlaylistScreen(viewmodel: PlaylistScreenViewModel) {

    val tracks = viewmodel.tracks.collectAsState()
    val selectedTracks = viewmodel.selectedTracks.collectAsState()
    val playlistTitle = viewmodel.playlistTitle.collectAsState()
    val showDeleteDialog = viewmodel.showDeleteDialog.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
    )
    {

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                playlistTitle.value,
                viewmodel::setPlaylistTitle,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = ir.hadiagdamapps.flow.ui.theme.Color.background,
                    unfocusedContainerColor = ir.hadiagdamapps.flow.ui.theme.Color.background
                )
            )
            IconButton(onClick = viewmodel::deletePlaylistClick) {
                Icon(Icons.Default.Delete, "delete icon")
            }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn {
            items(tracks.value.sortedByDescending { selectedTracks.value.contains(it.id) }) { track ->
                PlaylistSongItem(
                    modifier = Modifier.animateItem(), // should be tested
                    title = track.title,
                    artist = track.artist,
                    selected = selectedTracks.value.contains(track.id),
                    onRadioButtonClick = { viewmodel.trackRadioClick(track.id) }
                )
            }
        }

        if (showDeleteDialog.value) {
            ConfirmDeletePlaylistDialog(yesClick = viewmodel::deleteDialogConfirm, deny = viewmodel::deleteDialogDeny)
        }
    }

}


@Preview
@Composable
private fun PlaylistScreenPreview() {
    FlowTheme {
        val viewmodel: PlaylistScreenViewModel = viewModel()
        Surface {
            PlaylistScreen(viewmodel)
        }
    }
}