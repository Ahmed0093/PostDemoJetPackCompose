package com.example.postdemojetpackcompose.di

import androidx.room.Room
import com.example.postdemojetpackcompose.BaseApplication
import com.example.postdemojetpackcompose.cache.DataBase
import com.example.postdemojetpackcompose.cache.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): DataBase {
        return Room
            .databaseBuilder(app, DataBase::class.java, DataBase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePostDao(db: DataBase): PostDao{
        return db.postDao
    }


}