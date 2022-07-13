package com.thk.datda.repository

import androidx.paging.PagingData
import com.thk.datda.datasource.RemoteDataSource
import com.thk.datda.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PostRepository {
    suspend fun getPosts(): List<Post>
    fun getUserPosts(): Flow<PagingData<Post>>
}

class PostRepositoryImpl @Inject constructor(private val dataSource: RemoteDataSource): PostRepository {
    override suspend fun getPosts(): List<Post> {
        return dataSource.getPosts()
    }

    override fun getUserPosts(): Flow<PagingData<Post>> {
        return dataSource.getUserPosts()
    }
}