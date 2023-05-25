package com.ahr.mandirinews.data.mapper

import com.ahr.mandirinews.data.local.entity.RecentSearchEntity
import com.ahr.mandirinews.domain.model.RecentSearch

/**
 * Mapper from HeadlineNewsDto to HeadlineNewsEntity
 */

fun List<RecentSearch>.recentSearchDomainsToEntities(): List<RecentSearchEntity> = map {
    it.recentSearchDomainsToEntity()
}

fun RecentSearch.recentSearchDomainsToEntity(): RecentSearchEntity =
    RecentSearchEntity(
        id = id,
        text = text
    )

/**
 * Mapper from HeadlineNewsEntity to HeadlineNews Domain
 */

fun List<RecentSearchEntity>.recentSearchEntitiesToDomains(): List<RecentSearch> = map {
    it.recentSearchEntityToDomain()
}

fun RecentSearchEntity.recentSearchEntityToDomain(): RecentSearch =
    RecentSearch(
        id = id,
        text = text
    )

