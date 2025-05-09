package ir.hadiagdamapps.flow.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.hadiagdamapps.flow.R
import ir.hadiagdamapps.flow.ui.theme.FlowTheme

@Composable
fun ConfirmDeletePlaylistDialog(
    yesClick: () -> Unit,
    deny: () -> Unit // dismiss
) {

    AlertDialog(
        icon = {},
        title = { Text(stringResource(R.string.confirm_delete_playlist_title)) },
        text = { Text(stringResource(R.string.confirm_delete_playlist_text)) },
        onDismissRequest = deny,
        confirmButton = { Button(yesClick) { Text(stringResource(R.string.confirm_delete_playlist_yes_button)) } },
        dismissButton = { Button(deny) { Text(stringResource(R.string.confirm_delete_playlist_no_button)) } }
    )

}
@Preview
@Composable
private fun ConfirmDeletePlaylistDialogPreview() {
    FlowTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            ConfirmDeletePlaylistDialog(yesClick = {}, deny = {})

        }
    }
}