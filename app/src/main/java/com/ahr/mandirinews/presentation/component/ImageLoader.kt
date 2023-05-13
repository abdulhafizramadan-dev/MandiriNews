package com.ahr.mandirinews.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ahr.mandirinews.R
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme

enum class ImageLoaderType(val icon: ImageVector) {
    Loading(icon = Icons.Outlined.Image),
    Error(icon = Icons.Outlined.BrokenImage),
}

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    type: ImageLoaderType = ImageLoaderType.Loading,
    imageSize: Dp = 64.dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = type.icon,
            contentDescription = stringResource(R.string.image_not_found),
            modifier = Modifier.size(imageSize),
            tint = MaterialTheme.colorScheme.onBackground.copy(.6f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImageLoader() {
    MandiriNewsTheme {
        ImageLoader()
    }
}