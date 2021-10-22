package com.example.postdemojetpackcompose.ui.theme.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.postdemojetpackcompose.MainCoroutineRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class PostListViewModelTest {

    private lateinit var postListViewModel: PostListViewModel
    private var mockPostRepository = mock<PostRepository>()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @Test
    fun testCheckGetPostsRepoCall() = mainCoroutineRule.runBlockingTest {
        postListViewModel = PostListViewModel(mockPostRepository)
        postListViewModel.getPostLists(1)
        verify(mockPostRepository, times(1)).getPostsByUserId(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    suspend fun check_getDetailsScreenData_Call() = mainCoroutineRule.runBlockingTest {
        postListViewModel = PostListViewModel(mockPostRepository)
        postListViewModel.getDetailScreenData(1)
        verify(mockPostRepository, times(1)).getDetailsScreenData(1)
    }

}