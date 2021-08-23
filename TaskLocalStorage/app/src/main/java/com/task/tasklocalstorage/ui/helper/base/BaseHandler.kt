package com.task.tasklocalstorage.ui.helper.base

import android.content.Intent
import android.net.Uri
import android.view.View
import com.task.tasklocalstorage.ui.helper.callback.OnViewClicked

interface BaseHandler {
    fun callHome()

    fun startNewIntent(intent: Intent)
    fun <T : BaseActivity>startNewIntent(tClass: Class<T>)
//    fun openSingleImagePicker(dataItemCallback: DataItemTwoHandler<Uri?, Int>)
//    fun checkThePermission(
//        title: String,
//        deniedMsg: String,
//        dataItemCallback: DataItemTwoHandler<Int, Int>,
//        vararg permissions: String
//    )
}