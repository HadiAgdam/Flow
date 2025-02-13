package ir.hadiagdamapps.flow.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.hadiagdamapps.flow.R

@Composable
fun ImageBox(modifier: Modifier = Modifier, albumUri: String?) {
    Box(
        modifier
            .size(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(androidx.compose.ui.graphics.Color.White)
    ) {

        Image(
            painter = painterResource(R.drawable.music_icon),
            contentDescription = "album image place holder",
            contentScale = ContentScale.Inside,
            modifier = Modifier.fillMaxSize()
        )
        AsyncImage(
            model = albumUri, contentDescription = "album image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}