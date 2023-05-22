package com.ahr.mandirinews.domain.repository

import androidx.paging.PagingData
import com.ahr.mandirinews.domain.model.HeadlineNews
import com.ahr.mandirinews.domain.model.News
import com.ahr.mandirinews.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MandiriNewsRepository {

    fun getHeadlineNews(
        country: String,
        apiKey: String,
    ): Flow<Resource<List<HeadlineNews>>>

    fun getNews(
        query: String,
        apiKey: String
    ): Flow<PagingData<News>>

    fun searchNews(
        query: String,
        apiKey: String
    ): Flow<PagingData<News>>

}