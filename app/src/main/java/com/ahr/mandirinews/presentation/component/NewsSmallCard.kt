package com.ahr.mandirinews.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.ahr.mandirinews.R
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsSmallCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    author: String,
    date: String,
    onCardClicked: () -> Unit = {},
    @DrawableRes previewImage: Int = R.drawable.logo_mandiri,
) {
    val context = LocalContext.current
    Card(
        onClick = onCardClicked,
        modifier = modifier.height(100.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
        ) {
            CoilImage(
                imageRequest = {
                    ImageRequest.Builder(context)
                        .data(imageUrl)
                        .crossfade(true)
                        .build()
                },
                previewPlaceholder = previewImage,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp)),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .weight(2f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                NewsSmallCardItemRow(
                    author = author,
                    date = date
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewNewsSmallCard() {
    MandiriNewsTheme {
        NewsSmallCard(
            imageUrl = "https://static.www.nfl.com/image/private/t_editorial_landscape_12_desktop/league/qctjhv74mkbijidnxysv",
            title = "US inflation eased to 4.9% in April as Fed tightening takes effect - Financial Times",
            author = "Nicholas Megaw",
            date = "10 Jan, 2021",
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
        )
    }
}
