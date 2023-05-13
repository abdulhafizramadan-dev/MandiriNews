package com.ahr.mandirinews.presentation.screen.home

import androidx.paging.PagingData
import com.ahr.mandirinews.domain.model.HeadlineNews
import com.ahr.mandirinews.domain.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val headlineNews: List<HeadlineNews> = emptyList(),
    val news: Flow<PagingData<News>> = flow {  },
    val error: String? = null
)
