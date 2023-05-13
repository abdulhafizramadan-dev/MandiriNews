package com.ahr.mandirinews.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme

enum class NewsSmallCardItemType(val icon: ImageVector) {
    Author(icon = Icons.Outlined.Person),
    Date(icon = Icons.Outlined.DateRange)
}

@Composable
fun NewsSmallCardItemRow(
    modifier: Modifier = Modifier,
    author: String,
    date: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NewsSmallCardItem(
            type = NewsSmallCardItemType.Author,
            label = author
        )
        Spacer(modifier = Modifier.width(8.dp))
        NewsSmallCardItem(
            type = NewsSmallCardItemType.Date,
            label = date
        )
    }
}

@Composable
fun NewsSmallCardItem(
    modifier: Modifier = Modifier,
    type: NewsSmallCardItemType,
    label: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = type.icon,
            contentDescription = type.name,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            maxLines = 1,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsSmallCardItemRow() {
    MandiriNewsTheme {
        NewsSmallCardItemRow(
            modifier = Modifier.padding(all = 16.dp),
            author = "Nicholas Megaw",
            date = "10 Jan, 2021"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsSmallCardItem() {
    MandiriNewsTheme {
        NewsSmallCardItem(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            type = NewsSmallCardItemType.Author,
            label = "Nicholas Megaw"
        )
    }
}
