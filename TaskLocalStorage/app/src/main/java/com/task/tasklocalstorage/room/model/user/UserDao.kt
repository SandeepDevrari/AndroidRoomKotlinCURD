package com.task.tasklocalstorage.room.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user:User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers() : Flow<List<User>>
}