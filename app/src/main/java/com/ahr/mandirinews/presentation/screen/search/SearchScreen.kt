package com.ahr.mandirinews.presentation.screen.search

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ahr.mandirinews.R
import com.ahr.mandirinews.presentation.component.NewsAlertDialog
import com.ahr.mandirinews.presentation.component.topappbar.SearchNewsTopAppApp
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
//@Destination(start = true)
@Destination
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchScreenUiState by searchViewModel.searchScreenUiState.collectAsState()
    val searchButtonState by searchViewModel.searchButtonEnabled.collectAsState(initial = false)

    val screenTypeState = searchScreenUiState.screenTypeState

    val searchQuery = searchScreenUiState.searchQuery
    val searchedNews = searchScreenUiState.news.collectAsLazyPagingItems()
    val recentSearch = searchScreenUiState.recentSearch
    val errorMessage = searchScreenUiState.errorMessage

    val dialogState = searchScreenUiState.dialogState
    val dialogTitle = searchScreenUiState.dialogTitle
    val dialogMessage = searchScreenUiState.dialogMessage

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(key1 = searchedNews.loadState.refresh) {
        when (val response = searchedNews.loadState.refresh) {
            is LoadState.Error -> searchViewModel.updateErrorMessage(response.error.message)
            is LoadState.NotLoading, LoadState.Loading -> searchViewModel.updateErrorMessage(null)
        }
    }

    if (dialogState) {
        NewsAlertDialog(
            title = dialogTitle,
            message = dialogMessage,
            confirmText = stringResource(R.string.delete),
            cancelText = stringResource(R.string.cancel),
            onConfirm = {
                searchViewModel.setDialogState(state = false)
                searchViewModel.actionRecentSearch()
                keyboardController?.show()
            },
            onCancel = {
                scope.launch {
                    searchViewModel.setDialogState(state = false)
                    delay(500)
                    keyboardController?.show()
                }
            }
        )
    }

    Scaffold(
        topBar = {
            SearchNewsTopAppApp(
                searchQuery = searchQuery,
                onSearchQueryChanged = { query ->
                    searchViewModel.updateScreenActionState(SearchScreenUiStateAction.NEW_SEARCH)
                    searchViewModel.updateSearchQuery(query)
                },
                onNavigateUpClicked = {
                    focusManager.clearFocus()
                    navigator.popBackStack()
                },
                onSearchButtonClicked = {
                    focusManager.clearFocus()
                    searchViewModel.actionRecentSearch()
                },
                searchButtonEnabled = searchButtonState,
                onSearchImeActionClicked = {
                    focusManager.clearFocus()
                    searchViewModel.actionRecentSearch()
                },
                focusRequester = focusRequester,
                onSearchInputFocusChanged = { focusState ->
                    searchViewModel.updateScreenTypeState(focusState)
                }
            )
        }, modifier = modifier
    ) { paddingValues ->

        when (screenTypeState) {
            SearchScreenUiStateType.FOCUSED -> {
                RecentSearchNewsContent(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    recentAllSearch = recentSearch,
                    onPickTextClicked = { text ->
                        searchViewModel.updateSearchQuery(text)
                        searchViewModel.updateScreenActionState(SearchScreenUiStateAction.RECENT_SEARCH)
                        keyboardController?.show()
                    },
                    onTextClicked = { history ->
                        focusManager.clearFocus()
                        searchViewModel.updateSearchQuery(history)
                        searchViewModel.updateScreenActionState(SearchScreenUiStateAction.RECENT_SEARCH)
                        searchViewModel.actionRecentSearch()
                    },
                    onLongClicked = { id, text ->
                        searchViewModel.updateTempRecentSearchId(id)
                        searchViewModel.updateScreenActionState(SearchScreenUiStateAction.DELETE_SEARCH)
                        searchViewModel.setDialogState(
                            state = true,
                            title = context.getString(R.string.label_dialog_delete_recent_search, text),
                            message = context.getString(R.string.desc_dialog_delete_recent_search),
                        )
                    }
                )
            }
            SearchScreenUiStateType.UNFOCUSED -> {
                when (searchedNews.loadState.refresh) {
                    is LoadState.Loading -> {
                        NewsLottieScreenAnimation(
                            rawRes = R.raw.lottie_search,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                    is LoadState.Error -> {
                        NewsLottieScreenAnimation(
                            rawRes = R.raw.lottie_network_error,
                            modifier = Modifier.padding(paddingValues),
                            description = errorMessage
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
                }
            }
        }
    }
}

@Composable
fun NewsLottieScreenAnimation(
    modifier: Modifier = Modifier,
    @RawRes rawRes: Int,
    description: String? = null
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(resId = rawRes))
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(256.dp),
                iterations = LottieConstants.IterateForever,
            )
            if (description != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    MandiriNewsTheme {
        SearchScreen()
    }
}