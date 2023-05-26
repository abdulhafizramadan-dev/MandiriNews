package com.ahr.mandirinews.presentation.component.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.mandirinews.R
import com.ahr.mandirinews.presentation.component.NewsOutlinedTextInput
import com.ahr.mandirinews.presentation.screen.search.SearchScreenUiStateType
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsTopAppApp(
    modifier: Modifier = Modifier,
    onNavigateUpClicked: () -> Unit = {},
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchButtonClicked: () -> Unit = {},
    searchButtonEnabled: Boolean = true,
    onSearchImeActionClicked: () -> Unit = {},
    focusRequester: FocusRequester = FocusRequester.Default,
    onSearchInputFocusChanged: (SearchScreenUiStateType) -> Unit = {}
) {

    val searchButtonBackgroundColor = if (searchButtonEnabled) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val searchButtonContentColor = if (searchButtonEnabled) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)

    val searchTrailingIcon: (@Composable () -> Unit)? = if (searchQuery.isNotEmpty()) {
        {
            IconButton(onClick = { onSearchQueryChanged("") }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.label_clear_search_query)
                )
            }
        }
    } else null

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateUpClicked) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.desc_back_to_home_screen)
                )
            }
        },
        title = {
            NewsOutlinedTextInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        val searchScreenUiStateType =
                            if (focusState.isFocused) SearchScreenUiStateType.FOCUSED else SearchScreenUiStateType.UNFOCUSED
                        onSearchInputFocusChanged(searchScreenUiStateType)
                    },
                text = searchQuery,
                onTextChanged = onSearchQueryChanged,
                placeholder = stringResource(id = R.string.placeholder_search_news),
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearchImeActionClicked()
                }),
                trailingIcon = searchTrailingIcon,
                focusRequester = focusRequester
            )
        },
        actions = {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .clickable(enabled = searchButtonEnabled) { onSearchButtonClicked() }
                    .background(searchButtonBackgroundColor)
                    .height(50.dp)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(R.string.desc_search_news),
                    tint = searchButtonContentColor
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchNewsTopAppApp() {
    MandiriNewsTheme {
        SearchNewsTopAppApp(
            searchQuery = "",
            onSearchQueryChanged = {}
        )
    }
}
