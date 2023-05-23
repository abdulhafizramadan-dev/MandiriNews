package com.ahr.mandirinews.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ahr.mandirinews.R
import com.ahr.mandirinews.domain.model.HeadlineNews
import com.ahr.mandirinews.domain.model.News
import com.ahr.mandirinews.presentation.component.NewsHeadingSection
import com.ahr.mandirinews.presentation.component.card.NewsHeadlineCard
import com.ahr.mandirinews.presentation.component.card.NewsHeadlineCardShimmer
import com.ahr.mandirinews.presentation.component.card.NewsSmallCard
import com.ahr.mandirinews.presentation.component.card.NewsSmallCardShimmer
import com.ahr.mandirinews.util.emptyString
import com.ahr.mandirinews.util.launchBrowser
import com.ahr.mandirinews.util.toLocalDate
import com.ahr.mandirinews.util.toNewsFormat

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    homeScreenUiState: HomeScreenUiState,
    pullRefreshState: PullRefreshState,
    loadingState: Boolean,
    refreshState: Boolean,
    headlineNews: List<HeadlineNews>,
    newsLazyPagingItems: LazyPagingItems<News>,
) {

    val context = LocalContext.current
    val primaryColor = MaterialTheme.colorScheme.primary.toArgb()

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ) {
        LazyColumn {
            item {
                NewsHeadingSection(
                    title = stringResource(id = R.string.label_headline_news),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                if (!(homeScreenUiState.headlineNews.isEmpty() || loadingState)) {
                    HorizontalPager(
                        pageCount = 10,
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        val headline = headlineNews[it]
                        NewsHeadlineCard(
                            title = headline.title,
                            imageUrl = headline.urlToImage ?: "",
                            date = headline.publishedAt.toLocalDate().toNewsFormat(),
                            source = headline.source.name,
                            modifier = Modifier
                                .clickable { context.launchBrowser(headline.url, primaryColor) }
                                .padding(horizontal = 8.dp)
                        )
                    }
                } else {
                    NewsHeadlineCardShimmer(
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            stickyHeader {
                NewsHeadingSection(
                    title = stringResource(R.string.label_all_news),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            items(
                count = newsLazyPagingItems.itemCount,
                key = newsLazyPagingItems.itemKey(key = { it.url }),
                contentType = newsLazyPagingItems.itemContentType()
            ) { index ->
                if (!(homeScreenUiState.headlineNews.isEmpty() || loadingState)) {
                    val news = newsLazyPagingItems[index] ?: News()
                    NewsSmallCard(
                        imageUrl = news.urlToImage ?: emptyString(),
                        title = news.title,
                        author = news.author,
                        date = news.publishedAt.toLocalDate().toNewsFormat(),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        onCardClicked = {
                            context.launchBrowser(news.url, primaryColor)
                        }
                    )
                } else {
                    NewsSmallCardShimmer(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                    )
                }

            }
        }
        PullRefreshIndicator(
            refreshing = refreshState,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.background
        )
    }
}