package com.ahr.mandirinews.di

import com.ahr.mandirinews.data.repository.MandiriNewsRepositoryImpl
import com.ahr.mandirinews.domain.repository.MandiriNewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindMandiriNewsRepository(
        mandiriNewsRepositoryImpl: MandiriNewsRepositoryImpl
    ): MandiriNewsRepository

}