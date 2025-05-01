package ir.hadiagdamapps.flow.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.flow.data.model.OrderMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderMenu(
    onDismissRequest: () -> Unit,
    onOrderSelected: (OrderMode) -> Unit,
    selected: OrderMode
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = {
            Column(Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                OrderMode.entries.forEach {
                    Text(it.label, modifier = Modifier.fillMaxWidth().padding(4.dp).clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                        onClick = { onOrderSelected(it) },
                    ).padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if(it == selected) ir.hadiagdamapps.flow.ui.theme.Color.primary else Color.White)
                }
            }

        }
    )
}


@Preview
@Composable
fun OrderMenuPreview() {
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            OrderMenu({}, {}, OrderMode.TITLE)
        }
    }
}


