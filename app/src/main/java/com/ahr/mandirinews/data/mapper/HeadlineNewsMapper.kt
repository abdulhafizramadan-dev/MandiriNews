package com.ahr.mandirinews.data.mapper

import com.ahr.mandirinews.data.local.entity.HeadlineNewsEntity
import com.ahr.mandirinews.data.local.entity.SourceEntity
import com.ahr.mandirinews.data.networking.response.HeadlineNewsDto
import com.ahr.mandirinews.data.networking.response.SourceDto
import com.ahr.mandirinews.domain.model.HeadlineNews
import com.ahr.mandirinews.domain.model.Source
import com.ahr.mandirinews.util.emptyString

/**
 * Mapper from HeadlineNewsDto to HeadlineNewsEntity
 */

fun List<HeadlineNewsDto>.headlineNewsDtosToEntities(): List<HeadlineNewsEntity> = map {
    it.headlineNewsDtoToEntity()
}

fun HeadlineNewsDto.headlineNewsDtoToEntity(): HeadlineNewsEntity =
    HeadlineNewsEntity(
        urlToImage = urlToImage,
        title = title ?: emptyString(),
        url = url ?: emptyString(),
        publishedAt = publishedAt ?: emptyString(),
        source = source?.sourceDtoToEntity()
    )

fun SourceDto.sourceDtoToEntity(): SourceEntity =
    SourceEntity(
        name = name ?: emptyString(),
        sourceId = sourceId
    )


/**
 * Mapper from HeadlineNewsEntity to HeadlineNews Domain
 */

fun List<HeadlineNewsEntity>.recentSearchEntitiesToDomains(): List<HeadlineNews> = map {
    it.headlineNewsEntityToDomain()
}

fun HeadlineNewsEntity.headlineNewsEntityToDomain(): HeadlineNews =
    HeadlineNews(
        urlToImage = urlToImage,
        title = title,
        url = url,
        publishedAt = publishedAt,
        source = source?.sourceEntityToDomain() ?: Source()
    )

fun SourceEntity.sourceEntityToDomain(): Source =
    Source(
        name = name,
        sourceId = sourceId
    )
