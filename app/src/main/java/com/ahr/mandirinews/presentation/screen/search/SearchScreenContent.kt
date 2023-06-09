package com.ahr.mandirinews.presentation.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ahr.mandirinews.R
import com.ahr.mandirinews.domain.model.News
import com.ahr.mandirinews.presentation.component.card.NewsSmallCard
import com.ahr.mandirinews.presentation.component.card.NewsSmallCardShimmer
import com.ahr.mandirinews.util.emptyString
import com.ahr.mandirinews.util.launchBrowser
import com.ahr.mandirinews.util.toLocalDate
import com.ahr.mandirinews.util.toNewsFormat

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    newsLazyPagingItems: LazyPagingItems<News>
) {

    val context = LocalContext.current
    val primaryColor = MaterialTheme.colorScheme.primary.toArgb()

    val isNewsNotEmpty = newsLazyPagingItems.itemCount > 0

    if (isNewsNotEmpty) {

        val prependLoadingState = newsLazyPagingItems.loadState.prepend == LoadState.Loading

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.fillMaxSize()
        ) {
            items(
                count = newsLazyPagingItems.itemCount,
                key = newsLazyPagingItems.itemKey(key = { it.url }),
                contentType = newsLazyPagingItems.itemContentType()
            ) { index ->
                val news = newsLazyPagingItems[index] ?: News()
                NewsSmallCard(
                    imageUrl = news.urlToImage ?: emptyString(),
                    title = news.title,
                    author = news.author,
                    date = news.publishedAt.toLocalDate().toNewsFormat(),
                    modifier = Modifier.fillMaxWidth(),
                    onCardClicked = {
                        context.launchBrowser(news.url, primaryColor)
                    }
                )
            }
            if (prependLoadingState) {
                items(count = 10) {
                    NewsSmallCardShimmer(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    } else {
        NewsLottieScreenAnimation(
            rawRes = R.raw.lottie_search_not_found,
            modifier = modifier
        )
    }
}