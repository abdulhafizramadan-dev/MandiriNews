package com.ahr.mandirinews.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ahr.mandirinews.di.NewsApiKeyQualifier
import com.ahr.mandirinews.domain.model.RecentSearch
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

    val searchButtonEnabled
        get() = _searchScreenUiState.map { value ->
            value.searchQuery.isNotEmpty()
        }

    init {
        getAllRecentSearch()
    }

    fun searchNews() {
        viewModelScope.launch {
            val searchQuery = _searchScreenUiState.value.searchQuery
            _searchScreenUiState.value = _searchScreenUiState.value.copy(
                news = mandiriNewsRepository.searchNews(query = searchQuery, apiKey = apiKey)
                    .cachedIn(viewModelScope)
            )
            resetTempRecentSearchId()
        }
    }

    private fun getAllRecentSearch() {
        viewModelScope.launch {
            mandiriNewsRepository.getAllRecentSearch().collect { recentSearch ->
                _searchScreenUiState.value = _searchScreenUiState.value.copy(
                    recentSearch = recentSearch
                )
            }
        }
    }

    fun actionRecentSearch() {
        when (_searchScreenUiState.value.screenActionState) {
            SearchScreenUiStateAction.NEW_SEARCH -> {
                insertRecentSearch()
                searchNews()
            }
            SearchScreenUiStateAction.DELETE_SEARCH -> deleteRecentSearch()
            SearchScreenUiStateAction.RECENT_SEARCH -> searchNews()
        }
    }

    private fun insertRecentSearch() {
        viewModelScope.launch {
            val searchQuery = _searchScreenUiState.value.searchQuery
            mandiriNewsRepository.insertRecentSearch(
                RecentSearch(text = searchQuery)
            )
            resetTempRecentSearchId()
        }
    }

    private fun deleteRecentSearch() {
        viewModelScope.launch {
            val recentSearchId = _searchScreenUiState.value.tempRecentSearchId
            mandiriNewsRepository.deleteRecentSearch(recentSearchId)
            resetTempRecentSearchId()
        }
    }

    fun clearRecentSearch() {
        viewModelScope.launch {
            mandiriNewsRepository.clearRecentSearch()
        }
    }

    fun setDialogState(
        state: Boolean,
        title: String = "",
        message: String = "",
    ) {
        _searchScreenUiState.value = _searchScreenUiState.value.copy(
            dialogState = state,
            dialogTitle = title,
            dialogMessage = message
        )
    }

    fun updateScreenTypeState(state: SearchScreenUiStateType) {
        _searchScreenUiState.value = _searchScreenUiState.value.copy(
            screenTypeState = state
        )
    }

    fun updateScreenActionState(state: SearchScreenUiStateAction) {
        _searchScreenUiState.value = _searchScreenUiState.value.copy(
            screenActionState = state
        )
    }

    fun updateFocusState(state: Boolean) {
        _searchScreenUiState.value = _searchScreenUiState.value.copy(
            isFocus = state
        )
    }

    fun updateSearchQuery(query: String) {
        _searchScreenUiState.value = _searchScreenUiState.value.copy(
            searchQuery = query
        )
    }

    fun updateTempRecentSearchId(id: Int) {
        _searchScreenUiState.value = _searchScreenUiState.value.copy(
            tempRecentSearchId = id
        )
    }

    fun resetTempRecentSearchId() {
        _searchScreenUiState.value = _searchScreenUiState.value.copy(
            tempRecentSearchId = 0
        )
    }

}