package com.thk.data.network

import com.thk.data.model.Photo
import com.thk.data.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {
    @GET("users/{userId}/posts")
    suspend fun getUserPosts(@Path("userId") userId: Int): Response<List<Post>>

    @GET("albums/{albumId}/photos")
    suspend fun getAlbumPhotos(@Path("albumId") albumId: Int): Response<List<Photo>>

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}