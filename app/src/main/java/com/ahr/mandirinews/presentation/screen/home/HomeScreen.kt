package com.ahr.mandirinews.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ahr.mandirinews.R
import com.ahr.mandirinews.domain.model.News
import com.ahr.mandirinews.presentation.component.NewsHeadingSection
import com.ahr.mandirinews.presentation.component.NewsHeadlineCard
import com.ahr.mandirinews.presentation.component.NewsHeadlineCardShimmer
import com.ahr.mandirinews.presentation.component.NewsSmallCard
import com.ahr.mandirinews.presentation.component.NewsSmallCardShimmer
import com.ahr.mandirinews.presentation.component.NewsTopAppBar
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme
import com.ahr.mandirinews.util.emptyString
import com.ahr.mandirinews.util.toLocalDate
import com.ahr.mandirinews.util.toNewsFormat
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarPosition
import com.stevdzasan.messagebar.rememberMessageBarState

@Suppress("UNUSED_PARAMETER")
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Destination(start = true)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    val homeScreenUiState by homeViewModel.homeScreenUiState.collectAsState()

    val messageBarState = rememberMessageBarState()

    val headlineNews = homeScreenUiState.headlineNews
    val newsLazyPagingItems = homeScreenUiState.news.collectAsLazyPagingItems()
    val loadingState = homeScreenUiState.isLoading
    val refreshState = homeScreenUiState.isRefresh

    LaunchedEffect(key1 = homeScreenUiState.error) {
        if (homeScreenUiState.error != null) {
            messageBarState.addError(Exception(homeScreenUiState.error))
        }
    }

    val pullRequestState = rememberPullRefreshState(
        refreshing = refreshState,
        onRefresh = {
            newsLazyPagingItems.refresh()
            homeViewModel.refresh()
        }
    )

    Scaffold(
        topBar = {
             NewsTopAppBar()
        },
        modifier = modifier
    ) { paddingValues ->
        ContentWithMessageBar(
            messageBarState = messageBarState,
            position = MessageBarPosition.BOTTOM,
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(state = pullRequestState)
            ) {
                LazyColumn {
                    item {
                        NewsHeadingSection(
                            title = stringResource(id = R.string.headline_news),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                    item {
                        if (!(homeScreenUiState.headlineNews.isEmpty() || loadingState)) {
                            HorizontalPager(
                                pageCount = 10,
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                NewsHeadlineCard(
                                    title = headlineNews[it].title,
                                    imageUrl = headlineNews[it].urlToImage ?: "",
                                    date = headlineNews[it].publishedAt.toLocalDate().toNewsFormat(),
                                    source = headlineNews[it].source.name,
                                    modifier = Modifier.padding(horizontal = 8.dp)
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
                            title = stringResource(R.string.all_news),
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
                                    .fillMaxWidth()
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
                    state = pullRequestState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    MandiriNewsTheme {
        HomeScreen()
    }
}
