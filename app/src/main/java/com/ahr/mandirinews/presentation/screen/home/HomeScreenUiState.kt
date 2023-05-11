package com.ahr.mandirinews.presentation.screen.home

import com.ahr.mandirinews.domain.model.HeadlineNews

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val headlineNews: List<HeadlineNews> = emptyList(),
    val error: String? = null
)
