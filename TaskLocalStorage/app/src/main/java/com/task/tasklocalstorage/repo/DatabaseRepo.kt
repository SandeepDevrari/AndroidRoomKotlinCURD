package com.task.tasklocalstorage.repo

import com.task.tasklocalstorage.room.UserDatabase
import com.task.tasklocalstorage.room.model.user.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepo @Inject constructor(private val database: UserDatabase) {

    suspend fun insertUser(user:User)= database.userDao().insert(user)

    fun getAllUsers() : Flow<List<User>>{
        return database.userDao().getAllUsers()
    }
}