package com.example.postdemojetpackcompose.network

import com.example.postdemojetpackcompose.network.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsApi {

    @GET("users")
    suspend fun getAllUsers(): Response<ArrayList<UserResponseItem>>

    @GET("comments")
    suspend fun getUserCommentsByPostId(
        @Query("postId") postId: Int
    ):Response< CommentsPostResponse>

    @GET("posts")
    suspend fun getUserPostsByUserId(
        @Query("userId") userId: Int
    ): Response<ArrayList<UserPostResponseItem>>
}

