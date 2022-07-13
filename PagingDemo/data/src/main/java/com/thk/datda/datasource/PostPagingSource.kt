package com.thk.datda.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thk.datda.model.Post
import com.thk.datda.network.ApiInterface
import okio.IOException
import retrofit2.HttpException

private const val STARTING_USER_ID = 1

class PostPagingSource(
    private val postApi: ApiInterface
) : PagingSource<Int, Post>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {

            // 맨처음 로딩할 때의 key값은 null이기 때문에 기본값 할당
            val userId = params.key ?: STARTING_USER_ID

            val response = postApi.getUserPosts(userId)
            val posts = response.body() ?: emptyList()

            val prevKey = if (userId > 1) userId - 1 else null
            val nextKey = if (posts.isEmpty()) null else userId + 1

            LoadResult.Page(
                data = posts,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
                state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}