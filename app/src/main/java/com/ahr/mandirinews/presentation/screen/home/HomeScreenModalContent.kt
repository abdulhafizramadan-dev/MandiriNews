package com.ahr.mandirinews.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.mandirinews.R
import com.ahr.mandirinews.domain.model.Country
import com.ahr.mandirinews.presentation.component.NewsOutlinedTextInput
import com.ahr.mandirinews.presentation.component.card.NewsCountryCard
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme
import com.ahr.mandirinews.util.listAvailableCountries

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenModalContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit = {},
    countryList: List<Country> = emptyList(),
    selectedCountry: Country,
    onCountryClicked: (Country) -> Unit = {}
) {
    Column(modifier = modifier) {
        NewsOutlinedTextInput(
            text = searchQuery,
            onTextChanged = onSearchQueryChanged,
            placeholder = stringResource(R.string.placeholder_search_country),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChanged("") }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = stringResource(
                                R.string.desc_clear_search_query
                            )
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = countryList, key = { it.id }) { country ->
                NewsCountryCard(
                    flag = country.flag,
                    title = country.name,
                    languages = country.languages,
                    isSelected = selectedCountry.id == country.id,
                    onCountryClicked = {
                        onCountryClicked(country)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreenModalContent() {
    MandiriNewsTheme {
        var text by remember {
            mutableStateOf("")
        }
        HomeScreenModalContent(
            searchQuery = text,
            onSearchQueryChanged = { text = it },
            modifier = Modifier.padding(16.dp),
            countryList = listAvailableCountries,
            selectedCountry = listAvailableCountries[0]
        )
    }
}
