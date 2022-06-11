package com.thk.data.datasource

import com.thk.data.model.NetworkState
import com.thk.data.model.Post
import com.thk.data.network.ApiInterface
import kotlinx.coroutines.delay
import retrofit2.HttpException
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getPosts(): NetworkState<List<Post>>
}

class RemoteDataSourceImpl @Inject constructor(private val api: ApiInterface) : RemoteDataSource {
    override suspend fun getPosts(): NetworkState<List<Post>> {
        return try {
            val response = api.getPosts()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                NetworkState.Success(body)
            } else {
                NetworkState.Error(response.code(), response.message())
            }
        } catch (e: HttpException) {
            NetworkState.Error(e.code(), e.message())
        } catch (throwable: Throwable) {
            NetworkState.Exception(throwable)
        }
    }
}