package com.ahr.mandirinews.data.mapper

import com.ahr.mandirinews.data.local.entity.NewsEntity
import com.ahr.mandirinews.data.networking.response.NewsDto
import com.ahr.mandirinews.domain.model.News
import com.ahr.mandirinews.util.emptyString

/**
 * Mapper from NewsDto to NewsEntity
 */

fun List<NewsDto>.newsEntitiesToDomains(): List<NewsEntity> = map {
    it.headlineNewsDtoToEntity()
}

fun NewsDto.headlineNewsDtoToEntity(): NewsEntity =
    NewsEntity(
        urlToImage = urlToImage,
        title = title ?: emptyString(),
        url = url ?: emptyString(),
        publishedAt = publishedAt ?: emptyString(),
        author = author ?: emptyString()
    )


/**
 * Mapper from NewsDto to News Domain
 */

fun List<NewsDto>.newsDtosToDomains(): List<News> = map {
    it.newsDtoToDomain()
}

fun NewsDto.newsDtoToDomain(): News =
    News(
        urlToImage = urlToImage,
        title = title ?: "",
        url = url ?: "",
        publishedAt = publishedAt ?: "",
        author = author ?: ""
    )


/**
 * Mapper from NewsEntity to News Domain
 */

fun NewsEntity.newsEntityToDomain(): News =
    News(
        urlToImage = urlToImage,
        title = title,
        url = url,
        publishedAt = publishedAt,
        author = author
    )
