package com.ahr.mandirinews.domain.model

import androidx.annotation.DrawableRes
import com.blongho.country_data.World
import com.blongho.country_data.Country as WorldCountryData

fun WorldCountryData.toCountryDomain(): Country {
    return Country(
        id = alpha2,
        name = name,
        languages = languages,
        flag = flagResource
    )
}

private val defaultCountry = World.getCountryFrom("us")

data class Country(
    val id: String = defaultCountry.alpha2,
    val name: String = defaultCountry.name,
    val languages: List<String> = defaultCountry.languages,
    @DrawableRes val flag: Int = defaultCountry.flagResource
)
