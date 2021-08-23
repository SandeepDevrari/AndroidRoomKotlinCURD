package com.task.tasklocalstorage.room.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_email")
    val userEmail:String,

    @ColumnInfo(name = "user_name")
    val userName:String,

    @ColumnInfo(name = "user_image")
    val userImage:String
)
