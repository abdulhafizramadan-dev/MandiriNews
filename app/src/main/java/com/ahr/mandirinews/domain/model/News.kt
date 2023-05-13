package com.ahr.mandirinews.domain.model

import com.ahr.mandirinews.util.emptyString

data class News(
    val id: Int = 0,
    val urlToImage: String? = null,
    val title: String = emptyString(),
    val author: String = emptyString(),
    val url: String = emptyString(),
    val publishedAt: String = emptyString(),
)
