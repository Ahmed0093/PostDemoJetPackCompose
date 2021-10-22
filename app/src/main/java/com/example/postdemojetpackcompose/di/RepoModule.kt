package com.example.postdemojetpackcompose.di

import com.example.postdemojetpackcompose.cache.PostDao
import com.example.postdemojetpackcompose.network.PostsApi
import com.example.postdemojetpackcompose.ui.theme.viewmodel.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {

    @ViewModelScoped
    @Provides
    fun providePostRepository(
        postApi: PostsApi,
        postDao : PostDao
    ): PostRepository {
        return PostRepository(
            postsApi = postApi,
            postDao = postDao
        )
    }





}











