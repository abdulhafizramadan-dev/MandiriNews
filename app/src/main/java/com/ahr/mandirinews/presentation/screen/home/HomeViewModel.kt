package com.ahr.mandirinews.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {

    private val _homeScreenUiState = MutableStateFlow(HomeScreenUiState())
    val homeScreenUiState: StateFlow<HomeScreenUiState> get() = _homeScreenUiState

    init {
        viewModelScope.launch {
            delay(2000L)
            mandiriNewsRepository.getHeadlineNews(country = "us", apiKey = "")
                .collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            _homeScreenUiState.value = _homeScreenUiState.value.copy(
                                isLoading = false,
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
                                error = null,
                                headlineNews = response.data
                            )
                        }
                    }
                }
        }
    }

}