package com.ahr.mandirinews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahr.mandirinews.data.local.dao.HeadlineNewsDao
import com.ahr.mandirinews.data.local.dao.NewsDao
import com.ahr.mandirinews.data.local.dao.RemoteKeyDao
import com.ahr.mandirinews.data.local.entity.HeadlineNewsEntity
import com.ahr.mandirinews.data.local.entity.NewsEntity
import com.ahr.mandirinews.data.local.entity.RemoteKey

@Database(
    entities = [
        HeadlineNewsEntity::class,
        NewsEntity::class,
        RemoteKey::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MandiriNewsDatabase : RoomDatabase() {

    abstract val headlineNewsDao: HeadlineNewsDao

    abstract val newsDao: NewsDao

    abstract val remoteKeyDao: RemoteKeyDao

    companion object {
        const val NAME = "mandiri-news.db"
    }

}
