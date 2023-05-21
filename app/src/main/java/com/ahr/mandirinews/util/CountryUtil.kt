package com.ahr.mandirinews.util

import com.ahr.mandirinews.domain.model.Country
import com.blongho.country_data.World

private val countryIso639Codes = listOf("ar", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "in", "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za")

val listAvailableCountries: List<Country> get() {
    return countryIso639Codes.map { countryCode ->
        val country = World.getCountryFrom(countryCode)
        Country(
            id = country.alpha2,
            name = country.name,
            languages = country.languages,
            flag = country.flagResource
        )
    }.sortedBy { it.name }
}