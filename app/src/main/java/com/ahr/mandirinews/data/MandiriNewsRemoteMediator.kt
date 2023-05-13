package com.ahr.mandirinews.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ahr.mandirinews.data.local.MandiriNewsDatabase
import com.ahr.mandirinews.data.local.entity.NewsEntity
import com.ahr.mandirinews.data.local.entity.RemoteKey
import com.ahr.mandirinews.data.mapper.toNewsEntities
import com.ahr.mandirinews.data.networking.service.NewsApiService

@OptIn(ExperimentalPagingApi::class)
class MandiriNewsRemoteMediator(
    private val newsApiService: NewsApiService,
    private val mandiriNewsDatabase: MandiriNewsDatabase,
    private val apiKey: String,
    private val query: String
) : RemoteMediator<Int, NewsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {

        val page: Int = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true
            )
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val newsResponse = newsApiService.getNews(
                query = query,
                page = page,
                pageSize = state.config.pageSize,
                apiKey = apiKey
            )
            val newsEntities = newsResponse.news?.toNewsEntities() ?: emptyList()
            val endOfPaginationReached = newsEntities.isEmpty()

            mandiriNewsDatabase.withTransaction {
                val newsDao = mandiriNewsDatabase.newsDao
                val remoteKeyDao = mandiriNewsDatabase.remoteKeyDao

                if (loadType == LoadType.REFRESH) {
                    newsDao.clearNews()
                    remoteKeyDao.clearRemoteKeys()
                }

                val prevPage = if (page > 1) page - 1 else null
                val nextPage = if (!endOfPaginationReached) page + 1 else null

                newsDao.upsertNews(newsEntities = newsEntities)

                val remoteKeyEntities = newsEntities.map { news ->
                    RemoteKey(
                        newsUrl = news.url,
                        prevKey = prevPage,
                        currentKey = page,
                        nextKey = nextPage
                    )
                }
                remoteKeyDao.upsertRemoteKeys(remoteKeys = remoteKeyEntities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: Throwable) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, NewsEntity>
    ): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { news ->
            mandiriNewsDatabase.remoteKeyDao.remoteKeyByNewsUrl(news.url)
        }
    }
}