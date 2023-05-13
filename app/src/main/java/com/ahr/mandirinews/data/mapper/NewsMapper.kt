package com.ahr.mandirinews.data.mapper

import com.ahr.mandirinews.data.local.entity.NewsEntity
import com.ahr.mandirinews.data.networking.response.NewsDto
import com.ahr.mandirinews.domain.model.News
import com.ahr.mandirinews.util.emptyString

/**
 * Mapper from NewsDto to NewsEntity
 */

fun List<NewsDto>.toNewsEntities(): List<NewsEntity> = map {
    it.toNewsEntity()
}

fun NewsDto.toNewsEntity(): NewsEntity =
    NewsEntity(
        urlToImage = urlToImage,
        title = title ?: emptyString(),
        url = url ?: emptyString(),
        publishedAt = publishedAt ?: emptyString(),
        author = author ?: emptyString()
    )


/**
 * Mapper from NewsEntity to News Domain
 */

fun List<NewsEntity>.toNewsDomains(): List<News> = map {
    it.toNewsDomain()
}

fun NewsEntity.toNewsDomain(): News =
    News(
        urlToImage = urlToImage,
        title = title ?: emptyString(),
        url = url ?: emptyString(),
        publishedAt = publishedAt ?: emptyString(),
        author = author ?: emptyString()
    )
