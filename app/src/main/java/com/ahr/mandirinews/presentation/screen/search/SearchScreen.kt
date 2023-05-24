package com.ahr.mandirinews.presentation.screen.search

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ahr.mandirinews.R
import com.ahr.mandirinews.presentation.component.topappbar.SearchNewsTopAppApp
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination(start = true)
//@Destination
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchScreenUiState by searchViewModel.searchScreenUiState.collectAsState()
    val searchButtonState by searchViewModel.searchButtonEnabled.collectAsState(initial = false)
    val firstLoadState = searchScreenUiState.isFirstLoad
    val searchQuery = searchScreenUiState.searchQuery
    val searchedNews = searchScreenUiState.news.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            SearchNewsTopAppApp(
                searchQuery = searchQuery,
                onSearchQueryChanged = searchViewModel::updateSearchQuery,
                onNavigateUpClicked = {
                    focusManager.clearFocus()
                    navigator.popBackStack()
                },
                onSearchButtonClicked = searchViewModel::searchNews,
                searchButtonEnabled = searchButtonState,
                onSearchImeActionClicked = {
                    focusManager.clearFocus()
                    searchViewModel.searchNews()
                },
                focusRequester = focusRequester
            )
        }, modifier = modifier
    ) { paddingValues ->

        if (!firstLoadState) {
            when (searchedNews.loadState.refresh) {
                is LoadState.Loading -> {
                    NewsLottieScreenAnimation(
                        rawRes = R.raw.lottie_search,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
                is LoadState.NotLoading -> {
                    SearchScreenContent(
                        newsLazyPagingItems = searchedNews,
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    )
                }
                is LoadState.Error -> {
                }
            }

        }
    }
}

@Composable
fun NewsLottieScreenAnimation(
    modifier: Modifier = Modifier,
    @RawRes rawRes: Int
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(resId = rawRes))
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(256.dp),
            iterations = LottieConstants.IterateForever,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    MandiriNewsTheme {
        SearchScreen()
    }
}