package com.ahr.mandirinews.data.networking.service

import com.ahr.mandirinews.data.networking.response.HeadlineNewsResponse
import com.ahr.mandirinews.data.networking.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET(GetHeadlineNews)
    suspend fun getHeadlineNews(
        @Query("country") country: String = "id",
        @Query("apiKey") apiKey: String,
    ): HeadlineNewsResponse

    @GET(Everything)
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String,
    ): NewsResponse

    companion object {
        const val GetHeadlineNews = "top-headlines"
        const val Everything = "everything"
    }
}