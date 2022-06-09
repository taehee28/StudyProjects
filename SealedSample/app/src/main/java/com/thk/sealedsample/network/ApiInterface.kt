package com.thk.sealedsample.network

import com.thk.sealedsample.models.Post
import retrofit2.http.GET

interface ApiInterface {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}