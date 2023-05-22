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

fun List<HeadlineNewsDto>.toHeadlineNewsEntities(): List<HeadlineNewsEntity> = map {
    it.newsDtoToEntity()
}

fun HeadlineNewsDto.newsDtoToEntity(): HeadlineNewsEntity =
    HeadlineNewsEntity(
        urlToImage = urlToImage,
        title = title ?: emptyString(),
        url = url ?: emptyString(),
        publishedAt = publishedAt ?: emptyString(),
        source = source?.toSourceEntity()
    )

fun SourceDto.toSourceEntity(): SourceEntity =
    SourceEntity(
        name = name ?: emptyString(),
        sourceId = sourceId
    )


/**
 * Mapper from HeadlineNewsEntity to HeadlineNews Domain
 */

fun List<HeadlineNewsEntity>.toHeadlineNewsDomains(): List<HeadlineNews> = map {
    it.toHeadlineNewsDomains()
}

fun HeadlineNewsEntity.toHeadlineNewsDomains(): HeadlineNews =
    HeadlineNews(
        urlToImage = urlToImage,
        title = title,
        url = url,
        publishedAt = publishedAt,
        source = source?.toSourceDomain() ?: Source()
    )

fun SourceEntity.toSourceDomain(): Source =
    Source(
        name = name,
        sourceId = sourceId
    )
