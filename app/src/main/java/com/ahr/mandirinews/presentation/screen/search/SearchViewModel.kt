package com.ahr.mandirinews.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ahr.mandirinews.di.NewsApiKeyQualifier
import com.ahr.mandirinews.domain.repository.MandiriNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mandiriNewsRepository: MandiriNewsRepository,
    @NewsApiKeyQualifier
    private val apiKey: String,
) : ViewModel() {

    private val _searchScreenUiState = MutableStateFlow(SearchScreenUiState())
    val searchScreenUiState get() = _searchScreenUiState.asStateFlow()

    val searchButtonEnabled get() = _searchScreenUiState.map { value ->
        !value.isFirstLoad && value.searchQuery.isNotEmpty()
    }

    fun searchNews() {
        viewModelScope.launch {
            setFirstLoadState(state = false)
            val searchQuery = _searchScreenUiState.value.searchQuery
            _searchScreenUiState.value = _searchScreenUiState.value.copy(
                news = mandiriNewsRepository.searchNews(query = searchQuery, apiKey = apiKey).cachedIn(viewModelScope)
            )
        }
    }

    fun updateSearchQuery(query: String) {
        _searchScreenUiState.value = _searchScreenUiState.value.copy(
            searchQuery = query
        )
    }

    private fun setFirstLoadState(state: Boolean) {
        _searchScreenUiState.value = _searchScreenUiState.value.copy(
            isFirstLoad = state
        )
    }

}