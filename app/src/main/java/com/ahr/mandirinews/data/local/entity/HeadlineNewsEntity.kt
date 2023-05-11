package com.ahr.mandirinews.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahr.mandirinews.util.emptyString

@Entity
data class HeadlineNewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val urlToImage: String? = null,
    val title: String = emptyString(),
    val publishedAt: String = emptyString(),
    val url: String = emptyString(),
    @Embedded
    val source: SourceEntity? = SourceEntity(),
)

data class SourceEntity(
    @PrimaryKey
    val name: String = emptyString(),
    val sourceId: String? = null
)