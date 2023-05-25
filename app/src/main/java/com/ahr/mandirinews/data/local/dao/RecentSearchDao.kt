package com.ahr.mandirinews.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahr.mandirinews.data.local.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM recentsearchentity")
    fun getAllRecentSearch(): Flow<List<RecentSearchEntity>>

    @Query("SELECT * FROM recentsearchentity WHERE id = :id")
    suspend fun getRecentSearch(id: Int): RecentSearchEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(recentSearchEntity: RecentSearchEntity)

    @Query("DELETE FROM recentsearchentity WHERE id = :id")
    suspend fun deleteRecentSearch(id: Int)

    @Query("DELETE FROM recentsearchentity")
    suspend fun clearRecentSearch()

}