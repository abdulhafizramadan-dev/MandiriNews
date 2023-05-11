package com.ahr.mandirinews.domain.model

import com.ahr.mandirinews.util.emptyString

data class HeadlineNews(
    val urlToImage: String? = null,
    val title: String = emptyString(),
    val url: String = emptyString(),
    val publishedAt: String = emptyString(),
    val source: Source = Source(),
    )

data class Source(
    val name: String = emptyString(),
    val sourceId: String? = null
)