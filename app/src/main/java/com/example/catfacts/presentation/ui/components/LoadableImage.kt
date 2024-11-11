package com.example.catfacts.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun LoadableImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build()
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f))
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 4.dp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        else if (painter.state is AsyncImagePainter.State.Error) {
            Icon(
                imageVector = Icons.Filled.Image,
                contentDescription = "Error",
                tint = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
