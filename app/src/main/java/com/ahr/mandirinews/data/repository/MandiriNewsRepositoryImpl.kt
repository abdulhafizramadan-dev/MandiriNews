package com.ahr.mandirinews.data.repository

import androidx.room.withTransaction
import com.ahr.mandirinews.data.local.MandiriNewsDatabase
import com.ahr.mandirinews.data.mapper.toHeadlineNewsDomain
import com.ahr.mandirinews.data.mapper.toHeadlineNewsEntities
import com.ahr.mandirinews.data.networking.service.NewsApiService
import com.ahr.mandirinews.domain.model.HeadlineNews
import com.ahr.mandirinews.domain.model.Resource
import com.ahr.mandirinews.domain.repository.MandiriNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MandiriNewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val mandiriNewsDatabase: MandiriNewsDatabase
) : MandiriNewsRepository {
    override fun getHeadlineNews(
        country: String,
        apiKey: String,
    ): Flow<Resource<List<HeadlineNews>>> = flow<Resource<List<HeadlineNews>>> {
        val headlineNewsResponse = newsApiService.getHeadlineNews(country = country, apiKey = apiKey)
        val headlineNewsDao = mandiriNewsDatabase.headlineNewsDao
        mandiriNewsDatabase.withTransaction {
            val headlineNewsEntities = headlineNewsResponse.headlineNews?.toHeadlineNewsEntities() ?: emptyList()
            headlineNewsDao.clearHeadlineNews()
            headlineNewsDao.upsertHeadlineNews(headlineNewsEntities)
        }
        val headlineNewsDomain = headlineNewsDao.getHeadlineNews().toHeadlineNewsDomain()
        emit(Resource.Success(headlineNewsDomain))
    }.catch { error ->
        val headlineNewsDomain = mandiriNewsDatabase.headlineNewsDao.getHeadlineNews().toHeadlineNewsDomain()
        emit(Resource.Error(error = error, data = headlineNewsDomain))
    }
}