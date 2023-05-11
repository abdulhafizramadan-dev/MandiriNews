package com.ahr.mandirinews.di

import android.content.Context
import androidx.room.Room
import com.ahr.mandirinews.data.local.MandiriNewsDatabase
import com.ahr.mandirinews.data.networking.service.NewsApiService
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

/**
 * Module for networking
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        chuckerClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NewsApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(chuckerClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsApiService(
        retrofit: Retrofit
    ): NewsApiService {
        return retrofit.create()
    }

}


/**
 * Module for local database
 */

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideNewsDatabase(
        @ApplicationContext context: Context
    ): MandiriNewsDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MandiriNewsDatabase::class.java,
            name = MandiriNewsDatabase.NAME
        ).build()
    }

}