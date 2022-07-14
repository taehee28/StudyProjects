package com.thk.datda.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.thk.datda.database.PostDatabase
import com.thk.datda.model.Post
import com.thk.datda.network.ApiInterface
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_USER_ID = 1

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val postApi: ApiInterface,
    private val database: PostDatabase
) : RemoteMediator<Int, Post>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Post>): MediatorResult {
        return try {
            val userId = when (loadType) {
                // 맨처음 로딩
                LoadType.REFRESH -> STARTING_USER_ID
                // 이전 페이지 로딩 (이전페이지 로딩 사용하지 않음)
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                // 다음 페이지 로딩
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    lastItem.userId + 1
                }
            }

            val response = postApi.getUserPosts(userId)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.postsDao().clearAll()
                }

                database.postsDao().insertAll(response?.body() ?: emptyList())
            }

            MediatorResult.Success(
                endOfPaginationReached = response.body().isNullOrEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}