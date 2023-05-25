package com.ahr.mandirinews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String
)
