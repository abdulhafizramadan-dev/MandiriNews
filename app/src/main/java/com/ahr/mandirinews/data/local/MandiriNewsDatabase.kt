package com.ahr.mandirinews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahr.mandirinews.data.local.dao.HeadlineNewsDao
import com.ahr.mandirinews.data.local.entity.HeadlineNewsEntity

@Database(
    entities = [HeadlineNewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MandiriNewsDatabase : RoomDatabase(){

    abstract val headlineNewsDao: HeadlineNewsDao

    companion object {
        const val NAME = "mandiri-news.db"
    }

}