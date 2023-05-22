package com.ahr.mandirinews.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ahr.mandirinews.di.NewsApiKeyQualifier
import com.ahr.mandirinews.domain.model.Country
import com.ahr.mandirinews.domain.model.Resource
import com.ahr.mandirinews.domain.repository.MandiriNewsRepository
import com.ahr.mandirinews.domain.repository.UserPreferencesRepository
import com.ahr.mandirinews.util.listAvailableCountries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mandiriNewsRepository: MandiriNewsRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    @NewsApiKeyQualifier
    private val apiKey: String,
) : ViewModel() {

    private val _homeScreenUiState = MutableStateFlow(
        HomeScreenUiState(
            news = mandiriNewsRepository
                .getNews(query = "crypto", apiKey = apiKey)
                .cachedIn(viewModelScope)
        )
    )

    val homeScreenUiState get() = _homeScreenUiState.asStateFlow()

    private val _countrySearchQuery = MutableStateFlow("")

    val countrySearchQuery get() = _countrySearchQuery.asStateFlow()

    @OptIn(FlowPreview::class)
    val countries: Flow<List<Country>>
        get() = countrySearchQuery
            .debounce(500)
            .distinctUntilChanged()
            .map { searchQuery ->
                val countries = if (searchQuery.isEmpty()) listAvailableCountries else
                    searchCountryByName(
                        searchQuery,
                        listAvailableCountries
                    )
                countries
            }

    init {
        getUserPreferences()
    }

    fun refresh(showRefreshIndication: Boolean = true) {
        _homeScreenUiState.value = _homeScreenUiState.value.copy(
            isRefresh = showRefreshIndication,
            isLoading = true
        )
        getHeadlineNews()
    }

    private fun getUserPreferences() {
        viewModelScope.launch {
            userPreferencesRepository.getLocalCountry()
                .distinctUntilChanged()
                .collectLatest { country ->
                    _homeScreenUiState.value = _homeScreenUiState.value.copy(
                        localeCountry = country
                    )
                    getHeadlineNews()
                }
        }
    }

    private fun getHeadlineNews() {
        val countryCode = homeScreenUiState.value.localeCountry.id
        viewModelScope.launch {
            delay(2000L)
            mandiriNewsRepository.getHeadlineNews(country = countryCode, apiKey = apiKey)
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

    private suspend fun searchCountryByName(
        countryName: String,
        countryList: List<Country>,
    ): List<Country> {
        return withContext(Dispatchers.Default) {
            countryList.filter { country ->
                country.name.contains(other = countryName, ignoreCase = true) ||
                        country.id.contains(other = countryName, ignoreCase = true)
            }
        }
    }

    fun searchCountry(country: String) {
        viewModelScope.launch { _countrySearchQuery.emit(country) }
    }

    fun setDialogState(
        state: Boolean,
        title: String = "",
        message: String = "",
        selectedCountry: Country? = null,
    ) {
        _homeScreenUiState.value = _homeScreenUiState.value.copy(
            dialogState = state,
            dialogTitle = title,
            dialogMessage = message,
            tempSelectedCountry = selectedCountry
        )
    }

    fun setUserLocaleLocation() {
        homeScreenUiState.value.tempSelectedCountry?.let { country ->
            viewModelScope.launch {
                userPreferencesRepository.setLocalCountry(country)
            }
        }
    }
}