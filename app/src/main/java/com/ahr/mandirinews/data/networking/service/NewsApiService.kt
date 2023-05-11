package com.ahr.mandirinews.data.networking.service

import com.ahr.mandirinews.data.networking.response.HeadlineNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET(GetHeadlineNews)
    suspend fun getHeadlineNews(
        @Query("country") country: String = "id",
        @Query("apiKey") apiKey: String,
    ): HeadlineNewsResponse

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val GetHeadlineNews = "top-headlines"
    }
}