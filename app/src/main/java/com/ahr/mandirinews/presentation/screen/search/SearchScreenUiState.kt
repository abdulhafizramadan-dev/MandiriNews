package com.ahr.mandirinews.presentation.screen.search

import androidx.paging.PagingData
import com.ahr.mandirinews.domain.model.News
import com.ahr.mandirinews.domain.model.RecentSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class SearchScreenUiState(
    val isFocus: Boolean = true,
    val searchQuery: String = "",
    val news: Flow<PagingData<News>> = flow {  },
    val recentSearch: List<RecentSearch> = emptyList(),
    val dialogState: Boolean = false,
    val dialogTitle: String = "",
    val dialogMessage: String = "",
    val tempRecentSearchId: Int = 0,
    val screenTypeState: SearchScreenUiStateType = SearchScreenUiStateType.FOCUSED,
    val screenActionState: SearchScreenUiStateAction = SearchScreenUiStateAction.NEW_SEARCH
)

enum class SearchScreenUiStateType {
    FOCUSED, UNFOCUSED
}

enum class SearchScreenUiStateAction {
    NEW_SEARCH, RECENT_SEARCH, DELETE_SEARCH
}
