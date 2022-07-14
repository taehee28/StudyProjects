package com.thk.datda.di

import com.thk.datda.database.PostDao
import com.thk.datda.database.PostDatabase
import com.thk.datda.datasource.RemoteDataSource
import com.thk.datda.network.ApiInterface
import com.thk.datda.repository.PostRepository
import com.thk.datda.repository.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePostRepository(remoteApi: ApiInterface, database: PostDatabase): PostRepository =
        PostRepositoryImpl(remoteApi, database)
}