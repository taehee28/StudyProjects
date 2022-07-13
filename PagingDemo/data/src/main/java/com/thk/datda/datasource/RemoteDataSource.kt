package com.thk.datda.datasource

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thk.datda.model.Post
import com.thk.datda.network.ApiInterface
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getPosts(): List<Post>
    fun getUserPosts(): Flow<PagingData<Post>>
}

class RemoteDataSourceImpl @Inject constructor(private val api: ApiInterface): RemoteDataSource {
    /**
     * 기본적인 api call
     */
    override suspend fun getPosts(): List<Post> {
        return try {
            val response = api.getPosts()
            check(response.isSuccessful)

            response.body() ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Post>()
        }
    }

    override fun getUserPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
            ),
            pagingSourceFactory = {
                PostPagingSource(postApi = api)
            }
        ).flow
    }
}