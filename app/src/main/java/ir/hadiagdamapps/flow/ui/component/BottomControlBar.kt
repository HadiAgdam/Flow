package ir.hadiagdamapps.flow.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.flow.R
import ir.hadiagdamapps.flow.ui.theme.Color
import ir.hadiagdamapps.flow.ui.theme.FlowTheme
import ir.hadiagdamapps.flow.utils.toMinutes

@Composable
fun BottomControlBar(
    modifier: Modifier = Modifier,
    progress: Float,
    onProgressChanged: (Float) -> Unit,
    duration: Long,
    songTitle: String,
    albumUri: String?,
    playing: Boolean,
    changePlayingStatues: () -> Unit,
    next: () -> Unit,
    previous: () -> Unit
) {

    var tempProgress by remember { mutableFloatStateOf(progress) }
    var changing = false

    LaunchedEffect(progress) {
        if (!changing) tempProgress = progress
    }

    Column(
        modifier
            .fillMaxWidth()
            .background(Color.surface)
            .padding(6.dp)
            .padding(top = 8.dp)
    ) {


        Row(Modifier.fillMaxWidth()) {
            ImageBox(
                Modifier,
                albumUri
            )
            Text(
                text = songTitle,
                maxLines = 1,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(6.dp)
                    .weight(1f),
                overflow = TextOverflow.Ellipsis
            )

            Row {
                IconButton(onClick = previous) {
                    Icon(
                        painterResource(R.drawable.previous_icon),
                        contentDescription = "previous"
                    )
                }

                IconButton(onClick = changePlayingStatues) {
                    Icon(
                        painterResource(if (playing) R.drawable.pause_icon else R.drawable.play_icon),
                        contentDescription = "pause play"
                    )
                }

                IconButton(onClick = next) {
                    Icon(
                        painterResource(R.drawable.next_icon),
                        contentDescription = "next"
                    )
                }
            }
        }

        Slider(
            value = tempProgress,
            onValueChange = { tempProgress = it; changing = true },
            onValueChangeFinished = {
                onProgressChanged(tempProgress)
                changing = false
            },
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                (duration * progress).toLong().toMinutes(),
                style = MaterialTheme.typography.bodySmall
            )
            Text(duration.toMinutes(), style = MaterialTheme.typography.bodySmall)
        }



    }
}


@Preview
@Composable
private fun BottomControlBarPreview() {
    FlowTheme {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            BottomControlBar(Modifier,.5f, {}, 60000, "Title of song", null, true, {}, {}, {})
        }
    }
}