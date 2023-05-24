package com.ahr.mandirinews.presentation.screen.search

import androidx.paging.PagingData
import com.ahr.mandirinews.domain.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class SearchScreenUiState(
    val isFirstLoad: Boolean = true,
    val searchQuery: String = "",
    val news: Flow<PagingData<News>> = flow {  }
)
