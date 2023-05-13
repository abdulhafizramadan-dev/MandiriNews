package com.ahr.mandirinews.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ahr.mandirinews.data.local.entity.HeadlineNewsEntity

@Dao
interface HeadlineNewsDao {

    @Query("SELECT * FROM headlinenewsentity LIMIT 10")
    suspend fun getHeadlineNews(): List<HeadlineNewsEntity>

    @Upsert
    suspend fun upsertHeadlineNews(
        headlineNewsEntities: List<HeadlineNewsEntity>
    )

    @Query("DELETE FROM headlinenewsentity")
    suspend fun clearHeadlineNews()

}