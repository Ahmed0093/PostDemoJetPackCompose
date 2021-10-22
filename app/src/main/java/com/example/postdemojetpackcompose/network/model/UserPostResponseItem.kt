package com.example.postdemojetpackcompose.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserPostResponseItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val body: String,
    val title: String,
    val userId: Int
)
@Entity
data class UserCommentResponseItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val postId: String,
    val name: String,
    val email: String,
    val body: String
)