package com.ahr.mandirinews.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.ahr.mandirinews.data.MandiriNewsRemoteMediator
import com.ahr.mandirinews.data.local.MandiriNewsDatabase
import com.ahr.mandirinews.data.mapper.toHeadlineNewsDomains
import com.ahr.mandirinews.data.mapper.toHeadlineNewsEntities
import com.ahr.mandirinews.data.mapper.toNewsDomain
import com.ahr.mandirinews.data.networking.service.NewsApiService
import com.ahr.mandirinews.domain.model.HeadlineNews
import com.ahr.mandirinews.domain.model.News
import com.ahr.mandirinews.domain.model.Resource
import com.ahr.mandirinews.domain.repository.MandiriNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

        val headlineNewsDomain = headlineNewsDao.getHeadlineNews().toHeadlineNewsDomains()
        emit(Resource.Success(headlineNewsDomain))
    }.catch { error ->
        val headlineNewsDomain = mandiriNewsDatabase.headlineNewsDao.getHeadlineNews().toHeadlineNewsDomains()
        emit(Resource.Error(error = error, data = headlineNewsDomain))
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getNews(
        query: String,
        language: String,
        apiKey: String,
    ): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                mandiriNewsDatabase.newsDao.getNews()
            },
            remoteMediator = MandiriNewsRemoteMediator(
                newsApiService = newsApiService,
                mandiriNewsDatabase = mandiriNewsDatabase,
                query = query,
                country = language,
                apiKey = apiKey
            )
        ).flow.map { pagingData ->
            pagingData.map { newsEntity -> newsEntity.toNewsDomain() }
        }
    }
}