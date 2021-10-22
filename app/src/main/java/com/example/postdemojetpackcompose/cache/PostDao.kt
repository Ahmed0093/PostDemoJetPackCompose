package com.example.postdemojetpackcompose.cache

import androidx.room.*
import com.example.postdemojetpackcompose.network.model.UserCommentResponseItem
import com.example.postdemojetpackcompose.network.model.UserPostResponseItem
import com.example.postdemojetpackcompose.network.model.UserResponseItem

@Dao
interface PostDao {

    @Insert
    suspend fun insertPost(userPostResponseItem: UserPostResponseItem) //:Long ??


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(userResponseItems: List<UserResponseItem>) //:Long ??

     @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertComments(userCommentResponseItems: List<UserCommentResponseItem>)

     @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPosts(userPostResponseItems : List<UserPostResponseItem>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertComment(comment: UserCommentResponseItem)//: LongArray



    @Transaction //For Thread Safe call
    @Query("SELECT * FROM UserResponseItem")
    fun getAllUsers(): List<UserResponseItem>?

    @Transaction //For Thread Safe call
    @Query("SELECT * FROM UserCommentResponseItem")
    fun getAllComments(): List<UserCommentResponseItem>?


    @Transaction //For Thread Safe call
    @Query("SELECT * FROM UserPostResponseItem")
    fun getAllPosts(): List<UserPostResponseItem>?


    @Transaction //For Thread Safe call
    @Query("Select * from UserPostResponseItem Where id = :postId")
    suspend fun getPostWithComments(postId: Int): List<PostWithComment>?
}

data class PostWithComment(
    @Embedded val post: UserPostResponseItem,
    @Relation(
        parentColumn = "id",
        entityColumn = "postId"
    )
    val comments: List<UserCommentResponseItem>
)
//data class PostWithUser(
//    @Embedded val post: UserPostResponseItem,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "userId"
//    )
//    val user: List<UserResponseItem>
//)
data class PostDetailUIModel(
    val post: UserPostResponseItem? = null,
    val userData: UserResponseItem? = null,
    val comments: List<UserCommentResponseItem>? = ArrayList()
)
