package com.thk.datda.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("users/{userId}/posts")
    suspend fun getPosts(@Query("userId") userId: Int)
}