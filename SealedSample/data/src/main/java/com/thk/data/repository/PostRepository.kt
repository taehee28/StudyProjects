package com.thk.data.repository

import com.thk.data.datasource.RemoteDataSource
import com.thk.data.model.NetworkState
import com.thk.data.model.Post
import javax.inject.Inject

interface PostRepository {
    suspend fun getPosts(): NetworkState<List<Post>>
}

class PostRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : PostRepository {
    override suspend fun getPosts(): NetworkState<List<Post>> {
        return remoteDataSource.getPosts()
    }
}