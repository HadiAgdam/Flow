package ir.hadiagdamapps.flow.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.hadiagdamapps.flow.R
import ir.hadiagdamapps.flow.ui.theme.FlowTheme

@Composable
fun NewPlaylistDialog(
    submitClick: (text: String) -> Unit,
    deny: () -> Unit
) {

    var text by remember { mutableStateOf("") }

    AlertDialog(
        icon = {},
        title = { Text(stringResource(R.string.new_playlist)) },
        text = {
            TextField(text, { text = it })
        },
        onDismissRequest = deny,
        confirmButton = { Button({
            submitClick(text)
        }) { Text(stringResource(R.string.ok)) } },
        dismissButton = { Button(deny) { Text(stringResource(R.string.cancel)) } }
    )

}
@Preview
@Composable
private fun ConfirmDeletePlaylistDialogPreview() {
    FlowTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            NewPlaylistDialog(submitClick = {}, deny = {})

        }
    }
}