package com.ahr.mandirinews.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ahr.mandirinews.R
import com.ahr.mandirinews.domain.model.Country
import com.ahr.mandirinews.domain.model.toCountryDomain
import com.ahr.mandirinews.domain.repository.UserPreferencesRepository
import com.blongho.country_data.World
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val mandiriNewsDataStore: DataStore<Preferences>,
    @ApplicationContext context: Context
) : UserPreferencesRepository {

    private val LOCALE_COUNTRY_KEY = stringPreferencesKey(context.getString(R.string.prefs_key_locale_country))
    private val DEFAULT_COUNTRY_CODE = context.getString(R.string.default_country_code)

    override fun getLocalCountry(): Flow<Country> {
        return mandiriNewsDataStore.data.map { prefereces ->
            val countryCode = prefereces[LOCALE_COUNTRY_KEY] ?: DEFAULT_COUNTRY_CODE
            val country = World.getCountryFrom(countryCode)
            country.toCountryDomain()
        }
    }

    override suspend fun setLocalCountry(country: Country) {
        mandiriNewsDataStore.edit { preferences ->
            preferences[LOCALE_COUNTRY_KEY] = country.id
        }
    }
}