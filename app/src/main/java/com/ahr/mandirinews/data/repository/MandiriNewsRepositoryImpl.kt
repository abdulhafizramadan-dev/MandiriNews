package com.ahr.mandirinews.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.ahr.mandirinews.data.MandiriNewsPagingSource
import com.ahr.mandirinews.data.MandiriNewsRemoteMediator
import com.ahr.mandirinews.data.local.MandiriNewsDatabase
import com.ahr.mandirinews.data.mapper.headlineNewsDtosToEntities
import com.ahr.mandirinews.data.mapper.newsEntityToDomain
import com.ahr.mandirinews.data.mapper.recentSearchDomainsToEntity
import com.ahr.mandirinews.data.mapper.recentSearchEntitiesToDomains
import com.ahr.mandirinews.data.networking.service.NewsApiService
import com.ahr.mandirinews.domain.model.HeadlineNews
import com.ahr.mandirinews.domain.model.News
import com.ahr.mandirinews.domain.model.RecentSearch
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

    private val recentSearchDao get() = mandiriNewsDatabase.recentSearchDao

    override fun getHeadlineNews(
        country: String,
        apiKey: String,
    ): Flow<Resource<List<HeadlineNews>>> = flow<Resource<List<HeadlineNews>>> {

        val headlineNewsResponse = newsApiService.getHeadlineNews(country = country, apiKey = apiKey)
        val headlineNewsDao = mandiriNewsDatabase.headlineNewsDao

        mandiriNewsDatabase.withTransaction {
            val headlineNewsEntities = headlineNewsResponse.headlineNews?.headlineNewsDtosToEntities() ?: emptyList()
            headlineNewsDao.clearHeadlineNews()
            headlineNewsDao.upsertHeadlineNews(headlineNewsEntities)
        }

        val headlineNewsDomain = headlineNewsDao.getHeadlineNews().recentSearchEntitiesToDomains()
        emit(Resource.Success(headlineNewsDomain))
    }.catch { error ->
        val headlineNewsDomain = mandiriNewsDatabase.headlineNewsDao.getHeadlineNews().recentSearchEntitiesToDomains()
        emit(Resource.Error(error = error, data = headlineNewsDomain))
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getNews(
        query: String,
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
                apiKey = apiKey
            )
        ).flow.map { pagingData ->
            pagingData.map { newsEntity -> newsEntity.newsEntityToDomain() }
        }
    }

    override fun searchNews(query: String, apiKey: String): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                MandiriNewsPagingSource(
                    newsApiService,
                    query,
                    apiKey
                )
            }
        ).flow
    }

    override fun getAllRecentSearch(): Flow<List<RecentSearch>> {
        return recentSearchDao.getAllRecentSearch().map {
            it.recentSearchEntitiesToDomains()
        }
    }

    override suspend fun insertRecentSearch(recentSearch: RecentSearch) {
        recentSearchDao.insertRecentSearch(recentSearch.recentSearchDomainsToEntity())
    }

    override suspend fun deleteRecentSearch(id: Int) {
        recentSearchDao.deleteRecentSearch(id)
    }

    override suspend fun clearRecentSearch() {
        recentSearchDao.clearRecentSearch()
    }
}