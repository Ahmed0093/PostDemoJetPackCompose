package com.example.postdemojetpackcompose.ui.theme.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postdemojetpackcompose.cache.PostDetailUIModel
import com.example.postdemojetpackcompose.network.DataSourceResult
import com.example.postdemojetpackcompose.network.model.UserPostResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PostListViewModel
@Inject
constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    val posts: MutableState<List<UserPostResponseItem>> = mutableStateOf(ArrayList())
    val postDetailUI: MutableState<PostDetailUIModel> = mutableStateOf(PostDetailUIModel())

    val loading = mutableStateOf(false)

    init {
        cacheUsers()
    }

    fun getPostLists(
        userId: Int
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val postsFromRepo = postRepository.getPostsByUserId(userId = userId)) {
                    is DataSourceResult.Success -> {
                        withContext(Dispatchers.Main) {
                            posts.value = postsFromRepo.data as List<UserPostResponseItem>
                        }
                    }
                    is DataSourceResult.Error -> {
                        //TODO Handle Error
                    }
                }
            }
        }
    }

    fun getDetailScreenData(
        postId: Int
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                when (val repoResult = postRepository.getDetailsScreenData(postId = postId)) {
                    is DataSourceResult.Success -> {
                        withContext(Dispatchers.Main) {
                            postDetailUI.value = repoResult.data as PostDetailUIModel
                        }

                    }
                    is DataSourceResult.Error -> {
                        //TODO SHow Error
                    }
                }
            }
        }
    }

    private fun cacheUsers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                postRepository.fetchUsers()
            }
        }
    }
}