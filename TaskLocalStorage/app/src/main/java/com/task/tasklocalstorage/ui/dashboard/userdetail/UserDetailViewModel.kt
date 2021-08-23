package com.task.tasklocalstorage.ui.dashboard.userdetail

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.lifecycle.ViewModel
import com.task.tasklocalstorage.room.UserDatabase
import com.task.tasklocalstorage.utils.exception.InputException
import javax.inject.Inject
import android.text.TextUtils
import androidx.lifecycle.viewModelScope
import com.task.tasklocalstorage.repo.DatabaseRepo
import com.task.tasklocalstorage.room.model.user.User
import com.task.tasklocalstorage.ui.helper.base.BaseViewModel
import com.task.tasklocalstorage.utils.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailViewModel @Inject constructor(context: Context, private val databaseRepo: DatabaseRepo) : BaseViewModel(context){

    val dataObserver:DataObserver

    init {
        dataObserver=DataObserver()
    }

    @Throws(InputException::class)
    fun validateUserData(){

        if(TextUtils.isEmpty(dataObserver.userProfile) || dataObserver.userProfile.trim().isEmpty()){
            throw InputException("Add User Profile first")
        }

        if(TextUtils.isEmpty(dataObserver.userName) || dataObserver.userName.trim().isEmpty()){
            throw InputException("User Name is invalid")
        }

        if(TextUtils.isEmpty(dataObserver.userEmail) || dataObserver.userEmail.trim().isEmpty()){
            throw InputException("User Email is invalid")
        }

        if(!isValidEmail(dataObserver.userEmail)){
            throw InputException("Email address is invalid")
        }

        var user= User(dataObserver.userEmail, dataObserver.userName, dataObserver.userProfile)
        updateUser(user)
    }

    fun updateUser(user:User) = viewModelScope.launch(Dispatchers.IO+exceptionHandler){
        databaseRepo.insertUser(user)

        dataObserver.userEmail=""
        dataObserver.userName=""
        //context.showSnackBar("User Added")
    }

    inner class DataObserver : BaseObservable(){
        var userName:String=""
        set(value) {
            field=value
            notifyChange()
        }

        var userEmail:String=""
            set(value) {
                field=value
                notifyChange()
            }

        var userProfile=""
            set(value) {
                field=value
                notifyChange()
            }
    }

}