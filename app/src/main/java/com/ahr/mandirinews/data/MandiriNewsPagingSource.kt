package com.ahr.mandirinews.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ahr.mandirinews.data.mapper.newsDtosToDomains
import com.ahr.mandirinews.data.networking.service.NewsApiService
import com.ahr.mandirinews.domain.model.News

class MandiriNewsPagingSource(
    private val newsApiService: NewsApiService,
    private val query: String,
    private val apiKey: String
) : PagingSource<Int, News>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val nextPageNumber = params.key ?: 1
            val newsResponse = newsApiService.getNews(
                query = query,
                page = nextPageNumber,
                apiKey = apiKey,
                pageSize = params.loadSize
            )
            val newsDomain = newsResponse.news?.newsDtosToDomains() ?: emptyList()
            LoadResult.Page(
                data = newsDomain,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}