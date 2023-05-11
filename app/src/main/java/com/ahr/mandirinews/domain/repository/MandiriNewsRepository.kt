package com.ahr.mandirinews.domain.repository

import com.ahr.mandirinews.domain.model.HeadlineNews
import com.ahr.mandirinews.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MandiriNewsRepository {

    fun getHeadlineNews(
        country: String,
        apiKey: String,
    ): Flow<Resource<List<HeadlineNews>>>

}