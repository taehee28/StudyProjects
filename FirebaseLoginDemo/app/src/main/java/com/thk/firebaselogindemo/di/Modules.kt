package com.thk.firebaselogindemo.di

import android.content.Context
import com.thk.data.auth.GoogleSignInService
import com.thk.data.auth.GoogleSignInServiceImpl
import com.thk.data.repository.LoginRepository
import com.thk.data.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideGoogleSignInService(@ApplicationContext context: Context): GoogleSignInService =
        GoogleSignInServiceImpl(context)
}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindLoginRepository(loginRepository: LoginRepositoryImpl): LoginRepository
}