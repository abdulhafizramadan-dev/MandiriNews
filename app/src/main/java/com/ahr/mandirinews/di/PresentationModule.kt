package com.ahr.mandirinews.di

import android.content.Context
import com.ahr.mandirinews.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @NewsApiKeyQualifier
    @Provides
    @Singleton
    fun provideNewsApiKey(
        @ApplicationContext context: Context
    ): String {
        return context.getString(R.string.news_api_key)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsApiKeyQualifier
