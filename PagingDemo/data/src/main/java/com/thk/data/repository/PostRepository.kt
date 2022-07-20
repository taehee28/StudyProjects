package com.thk.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thk.data.database.PostDatabase
import com.thk.data.model.Photo
import com.thk.data.model.Post
import com.thk.data.network.ApiInterface
import com.thk.data.paging.PhotoRemoteMediator
import com.thk.data.paging.PostRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PostRepository {
    fun getUserPosts(): Flow<PagingData<Post>>
    fun getAlbumPhotos(): Flow<PagingData<Photo>>
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

    override fun getAlbumPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            remoteMediator = PhotoRemoteMediator(
                remoteApi,
                database
            )
        ) {
            database.photoDao().getAlbumPhotos()
        }.flow
    }
}