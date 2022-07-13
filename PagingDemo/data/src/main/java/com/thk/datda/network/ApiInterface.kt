package com.thk.datda.network

import com.thk.datda.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("users/{userId}/posts")
    suspend fun getUserPosts(@Path("userId") userId: Int): Response<List<Post>>

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}