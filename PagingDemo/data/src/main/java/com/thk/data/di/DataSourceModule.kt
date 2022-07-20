package com.thk.data.di

import com.thk.data.datasource.RemoteDataSource
import com.thk.data.datasource.RemoteDataSourceImpl
import com.thk.data.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(apiInterface: ApiInterface): RemoteDataSource =
        RemoteDataSourceImpl(apiInterface)
}