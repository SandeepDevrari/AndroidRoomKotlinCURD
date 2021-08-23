package com.task.tasklocalstorage.ui.helper.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.task.tasklocalstorage.R
import com.task.tasklocalstorage.utils.util.hideCustomDialog
import com.task.tasklocalstorage.utils.util.showSnackBar
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

open class BaseViewModel constructor(val context: Context) : ViewModel(){
    protected val exceptionHandler = CoroutineExceptionHandler{_, exception->
        Timber.e(exception)
        context.hideCustomDialog()
        context.showSnackBar(exception.message?:context.getString(R.string.msg_somthing_went_wrong))
    }
}