package com.task.tasklocalstorage.ui.dashboard

import android.content.Context
import androidx.lifecycle.*
import com.task.tasklocalstorage.repo.DatabaseRepo
import com.task.tasklocalstorage.room.model.user.User
import com.task.tasklocalstorage.ui.helper.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllUserViewModel @Inject constructor(context: Context, private val databaseRepo: DatabaseRepo) : BaseViewModel(context){


    fun getAllUsers() : LiveData<List<User>>{
        return databaseRepo.getAllUsers().asLiveData()
    }
}