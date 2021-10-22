package com.example.postdemojetpackcompose.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.postdemojetpackcompose.network.model.UserCommentResponseItem
import com.example.postdemojetpackcompose.network.model.UserPostResponseItem
import com.example.postdemojetpackcompose.network.model.UserResponseItem

@Database(entities = [UserPostResponseItem::class, UserCommentResponseItem::class, UserResponseItem::class], version = 1,exportSchema = false)
abstract class DataBase: RoomDatabase() {

    abstract val postDao: PostDao

    companion object{
        val DATABASE_NAME: String = "recipe_db"
    }

//    companion object {
//        @Volatile
//        private var INSTANCE: DataBase? = null
//
//        fun getInstance(context: Context): DataBase {
//            synchronized(this) {
//                return INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    DataBase::class.java,
//                    "school_db"
//                ).build().also {
//                    INSTANCE = it
//                }
//            }
//        }
//    }


}