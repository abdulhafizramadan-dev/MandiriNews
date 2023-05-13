package com.ahr.mandirinews.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ahr.mandirinews.data.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM newsentity")
    fun getNews(): PagingSource<Int, NewsEntity>

    @Upsert
    suspend fun upsertNews(newsEntities: List<NewsEntity>)

    @Query("DELETE FROM newsentity")
    suspend fun clearNews()

}