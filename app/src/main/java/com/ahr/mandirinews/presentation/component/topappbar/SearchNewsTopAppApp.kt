package com.ahr.mandirinews.presentation.component.topappbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ahr.mandirinews.R
import com.ahr.mandirinews.presentation.component.NewsOutlinedTextInput
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsTopAppApp(
    modifier: Modifier = Modifier,
    onNavigateUpClicked: () -> Unit = {},
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchButtonClicked: () -> Unit = {}
) {
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
                modifier = Modifier.fillMaxWidth(),
                text = searchQuery,
                onTextChanged = onSearchQueryChanged,
                placeholder = stringResource(id = R.string.placeholder_search_news)
            )
        },
        actions = {
            IconButton(onClick = onSearchButtonClicked) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(R.string.desc_search_news)
                )
            }
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
