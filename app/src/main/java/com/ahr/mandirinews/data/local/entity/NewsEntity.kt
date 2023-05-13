package com.ahr.mandirinews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahr.mandirinews.util.emptyString

@Entity
data class NewsEntity(
    val urlToImage: String? = null,
    val title: String = emptyString(),
    val author: String = emptyString(),
    val publishedAt: String = emptyString(),
    @PrimaryKey
    val url: String = emptyString(),
)
