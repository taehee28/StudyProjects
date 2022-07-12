package com.thk.datda.di

import com.thk.datda.datasource.RemoteDataSource
import com.thk.datda.datasource.RemoteDataSourceImpl
import com.thk.datda.network.ApiInterface
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