package com.ahr.mandirinews.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ahr.mandirinews.di.NewsApiKeyQualifier
import com.ahr.mandirinews.domain.model.Resource
import com.ahr.mandirinews.domain.repository.MandiriNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mandiriNewsRepository: MandiriNewsRepository,
    @NewsApiKeyQualifier
    private val apiKey: String
) : ViewModel() {

    private val _homeScreenUiState = MutableStateFlow(
        HomeScreenUiState(
            news = mandiriNewsRepository
                .getNews(query = "crypto", apiKey = apiKey)
                .cachedIn(viewModelScope)
        )
    )
    val homeScreenUiState: StateFlow<HomeScreenUiState> get() = _homeScreenUiState

    init {
        getHeadlineNews()
    }

    fun refresh() {
        _homeScreenUiState.value = _homeScreenUiState.value.copy(
            isRefresh = true,
            isLoading = true
        )
        getHeadlineNews()
    }

    private fun getHeadlineNews() {
        viewModelScope.launch {
            delay(2000L)
            mandiriNewsRepository.getHeadlineNews(country = "us", apiKey = apiKey)
                .collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            _homeScreenUiState.value = _homeScreenUiState.value.copy(
                                isLoading = false,
                                isRefresh = false,
                                error = response.error.message,
                                headlineNews = response.data ?: emptyList()
                            )
                        }
                        is Resource.Loading -> {
                            _homeScreenUiState.value = _homeScreenUiState.value.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _homeScreenUiState.value = _homeScreenUiState.value.copy(
                                isLoading = false,
                                isRefresh = false,
                                error = null,
                                headlineNews = response.data
                            )
                        }
                    }
                }

        }

    }

}