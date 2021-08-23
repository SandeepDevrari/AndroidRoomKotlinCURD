package com.task.tasklocalstorage.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.tasklocalstorage.room.model.user.User
import com.task.tasklocalstorage.room.model.user.UserDao

@Database(entities = arrayOf(User::class), version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){

    abstract fun userDao():UserDao

}