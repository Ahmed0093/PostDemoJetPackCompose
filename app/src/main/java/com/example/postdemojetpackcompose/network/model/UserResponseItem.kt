package com.example.postdemojetpackcompose.network.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class UserResponseItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
//    @Ignore
//    val address: Address,
//    @Ignore
//    val company: Company,
    val email: String,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)
