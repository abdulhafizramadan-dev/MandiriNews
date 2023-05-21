package com.ahr.mandirinews.domain.repository

import com.ahr.mandirinews.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getLocalCountry(): Flow<Country>

    suspend fun setLocalCountry(country: Country)

}