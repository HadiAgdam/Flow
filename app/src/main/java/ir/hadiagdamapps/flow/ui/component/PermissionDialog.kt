package ir.hadiagdamapps.flow.ui.component

import androidx.compose.foundation.layout.Box
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
fun PermissionDialog(onConfirmClick: () -> Unit) {
    AlertDialog(
        icon = {},
        title = { Text(stringResource(R.string.permission_dialog_title)) },
        text = { Text(stringResource(R.string.permission_dialog_content)) },
        onDismissRequest = {},
        confirmButton = { Button(onConfirmClick) { Text("open settings") } })
}


@Preview
@Composable
private fun PermissionDialogPreview() {
    FlowTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            PermissionDialog {}

        }
    }
}