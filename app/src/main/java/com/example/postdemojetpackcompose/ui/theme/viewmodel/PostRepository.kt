package com.example.postdemojetpackcompose.ui.theme.viewmodel

import com.example.postdemojetpackcompose.cache.PostDao
import com.example.postdemojetpackcompose.cache.PostDetailUIModel
import com.example.postdemojetpackcompose.network.NetworkRoute.makeApiCall
import com.example.postdemojetpackcompose.network.PostsApi
import com.example.postdemojetpackcompose.network.DataSourceResult
import com.example.postdemojetpackcompose.network.ApiResponseResult
import com.example.postdemojetpackcompose.network.model.UserCommentResponseItem
import com.example.postdemojetpackcompose.network.model.UserPostResponseItem
import com.example.postdemojetpackcompose.network.model.UserResponseItem

class PostRepository(
    private val postsApi: PostsApi,
    val postDao: PostDao
) {
    private suspend fun fetchPosts(userId: Int): ApiResponseResult<ArrayList<UserPostResponseItem>> {
        return makeApiCall {
            postsApi.getUserPostsByUserId(userId)
        }
    }

    suspend fun getPostsByUserId(userId: Int): DataSourceResult<ArrayList<UserPostResponseItem>> {
        var cachedPosts = postDao.getAllPosts()
        if (cachedPosts.isNullOrEmpty()) {
            //get Data from Remote
            when (val apiResponse = fetchPosts(userId = userId)) {
                is ApiResponseResult.Success -> {
                    cachedPosts = apiResponse.data as? List<UserPostResponseItem>?
                }
                is ApiResponseResult.Error -> {
                    return DataSourceResult.Error(apiResponse.errorMsg)
                }
            }
        }
        return DataSourceResult.Success(cachedPosts)
    }

    suspend fun getDetailsScreenData(postId: Int): DataSourceResult<PostDetailUIModel> {
        val cachedPostWithComments =
            postDao.getAllComments()?.filter { it.postId == postId.toString() }
        if (cachedPostWithComments.isNullOrEmpty()) {
            when (val apiResult = fetchComments(postId = postId)) {
                is ApiResponseResult.Success -> {
                    (apiResult.data as? List<UserCommentResponseItem>)?.let {
                        postDao.insertComments(it)
                    }

                }
                is ApiResponseResult.Error -> {

                }
            }
        }
        return DataSourceResult.Success(getDetailDataFromCache(postId = postId))
    }

    private fun getDetailDataFromCache(postId: Int): PostDetailUIModel {
        val comments = postDao.getAllComments()?.filter { it.postId == postId.toString() }
        val postDetailData = postDao.getAllPosts()?.firstOrNull { it.id == postId }
        val userData = postDao.getAllUsers()?.firstOrNull { it.id == postDetailData?.userId }

        return PostDetailUIModel(post = postDetailData, userData = userData, comments = comments)
    }


    private suspend fun fetchComments(postId: Int): ApiResponseResult<ArrayList<UserCommentResponseItem>> {
        return makeApiCall {
            postsApi.getUserCommentsByPostId(postId)
        }
    }

    private suspend fun fetchAllUsers(): ApiResponseResult<ArrayList<UserResponseItem>> {
        return makeApiCall {
            postsApi.getAllUsers()
        }
    }

    suspend fun fetchUsers() {
        val cachedUsers = postDao.getAllUsers()
        if (cachedUsers.isNullOrEmpty()) {
            when (val apiResult = fetchAllUsers()) {
                is ApiResponseResult.Success -> {
                    (apiResult.data as? List<UserResponseItem>)?.let {
                        postDao.insertUsers(it)
                    }
                }
                is ApiResponseResult.Error -> {

                }
            }
        }
    }
}


