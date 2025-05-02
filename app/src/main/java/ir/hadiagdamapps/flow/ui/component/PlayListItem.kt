package ir.hadiagdamapps.flow.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.flow.ui.theme.Color
import ir.hadiagdamapps.flow.ui.theme.FlowTheme

@Composable
fun PlayListItem(
    text: String,
    onEditClick: () -> Unit,
    onClick: () -> Unit,
    selected: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .background(if (selected) androidx.compose.ui.graphics.Color.DarkGray else Color.surface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text)
        IconButton(modifier = Modifier.size(36.dp).clip(RectangleShape), onClick = onEditClick,) {
            Icon(imageVector = Icons.Default.Create, contentDescription = null)
        }
    }
}


@Preview
@Composable
private fun PlayListItemPreview() {
    FlowTheme {
        Surface {
            Column {
                for (i in 1..10) {
                    var selected by remember { mutableStateOf(false) }

                    PlayListItem(
                        text = "Playlist $i",
                        onClick = { selected = !selected },
                        onEditClick = {},
                        selected = selected
                    )
                }
            }
        }
    }
}