package com.ahr.mandirinews.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahr.mandirinews.R
import com.ahr.mandirinews.presentation.component.NewsHeadingSection
import com.ahr.mandirinews.presentation.component.NewsHeadlineCard
import com.ahr.mandirinews.presentation.component.NewsHeadlineCardShimmer
import com.ahr.mandirinews.presentation.component.NewsSmallCard
import com.ahr.mandirinews.presentation.component.NewsTopAppBar
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Suppress("UNUSED_PARAMETER")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Destination(start = true)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    val homeScreenUiState by homeViewModel.homeScreenUiState.collectAsState()

    val headlineNews = homeScreenUiState.headlineNews

    Scaffold(
        topBar = {
             NewsTopAppBar()
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            item {
                NewsHeadingSection(
                    title = stringResource(id = R.string.headline_news),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                if (homeScreenUiState.headlineNews.isEmpty()) {
                    NewsHeadlineCardShimmer(
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    HorizontalPager(
                        pageCount = 10,
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        NewsHeadlineCard(
                            title = headlineNews[it].title,
                            imageUrl = headlineNews[it].urlToImage ?: "",
                            date = "10 Jan, 2021",
                            source = headlineNews[it].source.name,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                }
            }
            stickyHeader { 
                NewsHeadingSection(
                    title = stringResource(R.string.all_news),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            items(10) {
                NewsSmallCard(
                    imageUrl = "https://static.www.nfl.com/image/private/t_editorial_landscape_12_desktop/league/qctjhv74mkbijidnxysv",
                    title = "US inflation eased to 4.9% in April as Fed tightening takes effect - Financial Times",
                    author = "Nicholas Megaw",
                    date = "10 Jan, 2021",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
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
