package com.thk.datda.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thk.datda.database.PostDatabase
import com.thk.datda.datasource.RemoteDataSource
import com.thk.datda.model.Post
import com.thk.datda.network.ApiInterface
import com.thk.datda.paging.PostRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PostRepository {
    fun getUserPosts(): Flow<PagingData<Post>>
}

@OptIn(ExperimentalPagingApi::class)
class PostRepositoryImpl @Inject constructor(
    private val remoteApi: ApiInterface,
    private val database: PostDatabase
) : PostRepository {
    override fun getUserPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            remoteMediator = PostRemoteMediator(
                remoteApi,
                database
            )
        ) {
            // Room에서 데이터를 PagingSource 타입으로 리턴하게 하여
            // PagingSource를 제공
            database.postsDao().getPosts()
        }.flow
    }
}