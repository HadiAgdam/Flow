package ir.hadiagdamapps.flow.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.hadiagdamapps.flow.R
import ir.hadiagdamapps.flow.ui.theme.FlowTheme
import ir.hadiagdamapps.flow.utils.toMinutes

@Composable
fun SongItem(
    modifier: Modifier = Modifier,
    albumUri: String?,
    title: String,
    duration: Long,
    click: () -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(72.dp)
        .clickable { click() }
        .padding(12.dp)) {


        ImageBox(albumUri = albumUri)

        Spacer(Modifier.width(12.dp))

        Text(
            text = title,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis
        )

        Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd) {
            Text(
                duration.toMinutes(),
                modifier = Modifier,
                style = MaterialTheme.typography.bodyMedium
            )
        }


    }


}

@Preview
@Composable
private fun SongItemPreview() {
    FlowTheme {
        Column(Modifier.fillMaxSize()) {
            SongItem(
                albumUri = null,
                title = "Song 1",
                duration = 126000
            ) {}
        }
    }
}