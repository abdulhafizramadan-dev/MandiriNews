package com.ahr.mandirinews.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.mandirinews.R
import com.ahr.mandirinews.presentation.component.NewsHeadingSection
import com.ahr.mandirinews.presentation.component.NewsHeadlineCard
import com.ahr.mandirinews.presentation.component.NewsSmallCard
import com.ahr.mandirinews.presentation.component.NewsTopAppBar
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
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
                HorizontalPager(
                    pageCount = 3,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    NewsHeadlineCard(
                        title = "US inflation eased to 4.9% in April as Fed tightening takes effect - Financial Times",
                        imageUrl = "https://s.abcnews.com/images/US/interview-abc-ml-230510_1683716960105_hpMain_16x9_992.jpg",
                        date = "10 Jan, 2021",
                        source = "Financial Times",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
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
