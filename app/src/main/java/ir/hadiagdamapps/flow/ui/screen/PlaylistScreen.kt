package ir.hadiagdamapps.flow.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.hadiagdamapps.flow.ui.component.ConfirmDeletePlaylistDialog
import ir.hadiagdamapps.flow.ui.component.PlaylistSongItem
import ir.hadiagdamapps.flow.ui.theme.Color
import ir.hadiagdamapps.flow.viewmodel.PlaylistScreenViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PlaylistScreen(viewmodel: PlaylistScreenViewModel, navHostController: NavHostController) {

    val tracks by viewmodel.tracks.collectAsState()
    val selectedTracks by viewmodel.selectedTracks.collectAsState()
    val playlistTitle = viewmodel.playlistTitle.collectAsState()
    val showDeleteDialog = viewmodel.showDeleteDialog.collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.selectedTracks.collectLatest {
            Log.e("tracks", selectedTracks.joinToString(","))
        }
    }

    LaunchedEffect(Unit) {
        viewmodel.navigateBack.collectLatest {
            if (it)
                navHostController.popBackStack()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.background)
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
            items(tracks) { track ->
                var selected by remember { mutableStateOf(selectedTracks.contains(track.id)) }
                PlaylistSongItem(
                    modifier = Modifier
                        .animateItem()
                        .background(Color.surface), // should be tested
                    title = track.title,
                    artist = track.artist,
                    selected = selected,
                    onRadioButtonClick = {
                        selected = !selected
                        viewmodel.trackRadioClick(track.id)
                    }
                )
            }
        }

        if (showDeleteDialog.value) {
            ConfirmDeletePlaylistDialog(
                yesClick = viewmodel::deleteDialogConfirm,
                deny = viewmodel::deleteDialogDeny
            )
        }
    }

}
