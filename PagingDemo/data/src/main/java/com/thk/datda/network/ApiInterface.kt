package com.thk.datda.network

import com.thk.datda.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("users/{userId}/posts")
    suspend fun getPosts(@Query("userId") userId: Int): Response<List<Post>>
}