@file:OptIn(ExperimentalPagingApi::class)

package com.thk.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.thk.data.database.PostDatabase
import com.thk.data.logd
import com.thk.data.model.Photo
import com.thk.data.network.ApiInterface
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_ALBUM_ID = 1

class PhotoRemoteMediator(
    private val api: ApiInterface,
    private val database: PostDatabase
) : RemoteMediator<Int, Photo>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Photo>): MediatorResult {
        return try {
            val albumId = when (loadType) {
                LoadType.REFRESH -> {
                    logd(">> LoadType.REFRESH")
                    STARTING_ALBUM_ID
                }
                LoadType.PREPEND -> {
                    logd(">> LoadType.PREPEND")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    logd(">> LoadType.APPEND")

                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    lastItem.albumId + 1
                }
            }

            logd(">> load data with albumId = $albumId")
            val response = api.getAlbumPhotos(albumId)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.photoDao().clearAll()
                }

                database.photoDao().insertAll(response?.body() ?: emptyList())
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