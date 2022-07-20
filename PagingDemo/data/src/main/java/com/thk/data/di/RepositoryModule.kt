package com.thk.data.di

import com.thk.data.database.PostDao
import com.thk.data.database.PostDatabase
import com.thk.data.datasource.RemoteDataSource
import com.thk.data.network.ApiInterface
import com.thk.data.repository.PostRepository
import com.thk.data.repository.PostRepositoryImpl
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