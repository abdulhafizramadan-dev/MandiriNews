package com.ahr.mandirinews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val newsUrl: String,
    val prevKey: Int?,
    val currentKey: Int,
    val nextKey: Int?
)