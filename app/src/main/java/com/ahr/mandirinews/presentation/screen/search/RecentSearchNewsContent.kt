package com.ahr.mandirinews.presentation.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.mandirinews.R
import com.ahr.mandirinews.domain.model.RecentSearch
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecentSearchNewsContent(
    modifier: Modifier = Modifier,
    recentAllSearch: List<RecentSearch> = emptyList(),
    onPickTextClicked: (text: String) -> Unit,
    onTextClicked: (text: String) -> Unit,
    onLongClicked: (id: Int, text: String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = recentAllSearch, key = { it.id }) { recent ->
            RecentSearchNewsItem(
                modifier = Modifier
                    .padding(start = 24.dp, end = 8.dp)
                    .fillMaxWidth()
                    .animateItemPlacement(),
                id = recent.id,
                text = recent.text,
                onTextClicked = onTextClicked,
                onPickTextClicked = onPickTextClicked,
                onLongClicked = onLongClicked
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecentSearchNewsItem(
    modifier: Modifier = Modifier,
    id: Int,
    text: String,
    onPickTextClicked: (text: String) -> Unit,
    onTextClicked: (text: String) -> Unit,
    onLongClicked: (id: Int, text: String) -> Unit
) {
    Row(
        modifier = Modifier
            .combinedClickable(
                onClick = { onTextClicked(text) },
                onLongClick = { onLongClicked(id, text) }
            )
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.History,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        IconButton(onClick = { onPickTextClicked(text) }) {
            Icon(
                imageVector = Icons.Outlined.ArrowUpward,
                contentDescription = stringResource(R.string.pick_the_history_news),
                modifier = Modifier.rotate(-45f)
            )
        }
    }
}

@Preview
@Composable
fun PreviewRecentSearchNewsContent() {
    MandiriNewsTheme {
        val recentSearches = (1..20).map {
            RecentSearch(it, "Sleeping to my fingers")
        }
        RecentSearchNewsContent(
            modifier = Modifier.fillMaxSize(),
            recentAllSearch = recentSearches,
            onPickTextClicked = { _ -> },
            onTextClicked = { _ -> },
            onLongClicked = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecentSearchNewsItem() {
    MandiriNewsTheme {
        RecentSearchNewsItem(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            id = 0,
            text = "Sleeping to my fingers",
            onTextClicked = { _ -> },
            onPickTextClicked = { _ -> },
            onLongClicked = { _, _ -> }
        )
    }
}
