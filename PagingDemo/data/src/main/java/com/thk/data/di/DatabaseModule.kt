package com.thk.data.di

import android.content.Context
import androidx.room.Room
import com.thk.data.database.DatabaseInfo
import com.thk.data.database.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePostDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            PostDatabase::class.java,
            DatabaseInfo.DATABASE_NAME
        ).build()
}