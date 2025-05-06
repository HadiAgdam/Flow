package ir.hadiagdamapps.flow.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.flow.ui.theme.FlowTheme

@Composable
fun PlaylistSongItem(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    selected: Boolean,
    onRadioButtonClick: () -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp, vertical = 4.dp)
        .height(64.dp)
        .background(ir.hadiagdamapps.flow.ui.theme.Color.background)
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
        ) {

        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,

                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = artist,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
            )
        }

        Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterEnd) {
            RadioButton(
                selected=selected,
                onClick = onRadioButtonClick
            )
        }


    }

}


@Preview
@Composable
private fun PlaylistSongItemPreview() {
    FlowTheme {
        Surface {
            Column(Modifier.fillMaxSize()) {
                for (i in 0..9)
                    PlaylistSongItem(
                        title = "item $i",
                        artist = "artist $i",
                        selected = i == 5 || i == 8,
                        onRadioButtonClick = {}
                    )
            }
        }
    }
}

