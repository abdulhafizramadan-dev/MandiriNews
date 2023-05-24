package com.ahr.mandirinews.presentation.screen.home

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ahr.mandirinews.R
import com.ahr.mandirinews.domain.model.HeadlineNews
import com.ahr.mandirinews.domain.model.News
import com.ahr.mandirinews.presentation.component.NewsAlertDialog
import com.ahr.mandirinews.presentation.component.topappbar.NewsTopAppBar
import com.ahr.mandirinews.presentation.component.topappbar.NewsTopAppBarAction
import com.ahr.mandirinews.presentation.screen.destinations.SearchScreenDestination
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarPosition
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.messagebar.rememberMessageBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("UNUSED_PARAMETER")
@OptIn(ExperimentalMaterialApi::class)
//@Destination(start = true)
@Destination
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    val homeScreenUiState by homeViewModel.homeScreenUiState.collectAsState()

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val messageBarState = rememberMessageBarState()

    val localeCountry = homeScreenUiState.localeCountry
    val headlineNews = homeScreenUiState.headlineNews
    val newsLazyPagingItems = homeScreenUiState.news.collectAsLazyPagingItems()
    val loadingState = homeScreenUiState.isLoading
    val refreshState = homeScreenUiState.isRefresh

    val dialogState = homeScreenUiState.dialogState
    val dialogTitle = homeScreenUiState.dialogTitle
    val dialogMessage = homeScreenUiState.dialogMessage

    val countrySearchQuery by homeViewModel.countrySearchQuery.collectAsState()
    val countryList by homeViewModel.countries.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = homeScreenUiState.error) {
        if (homeScreenUiState.error != null) {
            messageBarState.addError(Exception(homeScreenUiState.error))
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshState,
        onRefresh = {
            newsLazyPagingItems.refresh()
            homeViewModel.refresh()
        }
    )

    val onLocaleActionClicked: () -> Unit = {
        scope.launch { modalBottomSheetState.show() }
    }

    val onSearchActionClicked: () -> Unit = {
        navigator.navigate(direction = SearchScreenDestination())
    }

    val onNotificationActionClicked: () -> Unit = {

    }

//    val modalShape =

    BackHandler(enabled = modalBottomSheetState.isVisible) {
        scope.launch { modalBottomSheetState.hide() }
    }

    if (dialogState) {
        NewsAlertDialog(
            title = dialogTitle,
            message = dialogMessage,
            confirmText = stringResource(R.string.change),
            cancelText = stringResource(R.string.cancel),
            onConfirm = {
                homeViewModel.setUserLocaleLocation()
                homeViewModel.setDialogState(state = false)
                scope.launch {
                    modalBottomSheetState.hide()
                    delay(500L)
                    homeViewModel.refresh(showRefreshIndication = false)
                    newsLazyPagingItems.retry()
                }
            },
            onCancel = {
                homeViewModel.setDialogState(state = false)
            }
        )
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = MaterialTheme.shapes.large,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            HomeScreenModalContent(
                searchQuery = countrySearchQuery,
                onSearchQueryChanged = homeViewModel::searchCountry,
                countryList = countryList,
                selectedCountry = localeCountry,
                onCountryClicked = { country ->
                    homeViewModel.setDialogState(
                        state = true,
                        title = context.getString(R.string.label_dialog_change_country, country.name),
                        message = context.getString(R.string.desc_dialog_change_country),
                        selectedCountry = country
                    )
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 16.dp)
            )
        },
    ) {
        HomeScreenScaffold(
            messageBarState = messageBarState,
            homeScreenUiState = homeScreenUiState,
            pullRefreshState = pullRefreshState,
            loadingState = loadingState,
            refreshState = refreshState,
            countryIcon = localeCountry.flag,
            headlineNews = headlineNews,
            newsLazyPagingItems = newsLazyPagingItems,
            onLocaleActionClicked = onLocaleActionClicked,
            onSearchActionClicked = onSearchActionClicked,
            onNotificationActionClicked = onNotificationActionClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreenScaffold(
    modifier: Modifier = Modifier,
    messageBarState: MessageBarState,
    homeScreenUiState: HomeScreenUiState,
    pullRefreshState: PullRefreshState,
    loadingState: Boolean,
    refreshState: Boolean,
    @DrawableRes countryIcon: Int,
    headlineNews: List<HeadlineNews>,
    newsLazyPagingItems: LazyPagingItems<News>,
    onLocaleActionClicked: () -> Unit = {},
    onSearchActionClicked: () -> Unit = {},
    onNotificationActionClicked: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            NewsTopAppBar(
                countryIcon = countryIcon,
                onActionButtonClicked = { actionType ->
                    when (actionType) {
                        NewsTopAppBarAction.Search -> onSearchActionClicked()
                        NewsTopAppBarAction.Locale -> onLocaleActionClicked()
                        NewsTopAppBarAction.Notification -> onNotificationActionClicked()
                    }
                },
            )
        },
        modifier = modifier
    ) { paddingValues ->
        ContentWithMessageBar(
            messageBarState = messageBarState,
            position = MessageBarPosition.BOTTOM,
            modifier = Modifier.padding(paddingValues)
        ) {
            HomeScreenContent(
                homeScreenUiState = homeScreenUiState,
                pullRefreshState = pullRefreshState,
                loadingState = loadingState,
                refreshState = refreshState,
                headlineNews = headlineNews,
                newsLazyPagingItems = newsLazyPagingItems
            )
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
