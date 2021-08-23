package com.task.tasklocalstorage.ui.helper.base

import android.content.Intent
import android.os.Build
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.task.tasklocalstorage.dependency.factory.BaseViewModelFactory
import com.task.tasklocalstorage.ui.dashboard.ActivityAllUser
import com.task.tasklocalstorage.ui.helper.callback.OnViewClicked
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), BaseHandler{
    @Inject
    lateinit var factory: BaseViewModelFactory

    protected fun <T: ViewModel> getViewModel(viewModel: Class<T>):T{
        return ViewModelProvider(this,factory).get(viewModel)
    }

    override fun callHome() {
        var intent: Intent = Intent(this, ActivityAllUser::class.java)
        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startNewIntent(intent)
    }


    override fun startNewIntent(intent: Intent) {
        startActivity(intent)
    }

    override fun <T : BaseActivity>startNewIntent(tClass: Class<T>) {
        var intent= Intent(this, tClass)
        startNewIntent(intent)
    }

//    override fun openSingleImagePicker(dataItemCallback: DataItemTwoHandler<Uri?, Int>) {
//        if (!isFinishing) {
//            checkThePermission(
//                resources.getString(R.string.text_accept_permission),
//                resources.getString(R.string.msg_please_allow_us_to_use_permission),
//                object : DataItemTwoHandler<Int, Int> {
//                    @SuppressLint("CheckResult")
//                    override fun onItemClicked(result: Int, r: Int) {
//                        if (result == com.dev.researchcrestacademy.utils.constants.AppLevelConstants.PERMISSION_ACCEPTED) {
//                            TedRxImagePicker.with(baseContext)
//                                .mediaType(MediaType.IMAGE)
//                                .cameraTileImage(R.drawable.ic_camera)
//                                .cameraTileBackground(R.color.white)
//                                .showCameraTile(true)
//                                .showTitle(true)
//                                .title(resources.getString(R.string.text_select_image))
//                                .backButton(R.drawable.ic_back)
//                                .start()
//                                .subscribe(
//                                    { uri -> dataItemCallback.onItemClicked(uri, 0) }
//                                ) { throwable ->
//                                    showSnackBar(throwable.localizedMessage)
//                                    dataItemCallback.onItemClicked(null, -1)
//                                }
//                        } else {
//                            showSnackBar(resources.getString(R.string.msg_image_permission_not_granted))
//                            dataItemCallback.onItemClicked(null, -1)
//                        }
//                    }
//                },
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            )
//        } else {
//            dataItemCallback.onItemClicked(null, -1)
//        }
//    }
//
//    @SuppressLint("CheckResult")
//    override fun checkThePermission(
//        title: String,
//        deniedMsg: String,
//        dataItemCallback: DataItemTwoHandler<Int, Int>,
//        vararg permissions: String
//    ) {
//        TedRx2Permission.with(this)
//            .setRationaleTitle(title)
//            .setRationaleMessage(deniedMsg)
//            .setPermissions(*permissions)
//            .setGotoSettingButton(true)
//            .setGotoSettingButtonText(resources.getString(R.string.text_open_setting))
//            .request()
//            .subscribe(object : Consumer<TedPermissionResult> {
//                override fun accept(t: TedPermissionResult?) {
//                    if (t?.isGranted() == true) {
//                        dataItemCallback.onItemClicked(AppLevelConstants.PERMISSION_ACCEPTED,-1);
//                    } else {
//                        dataItemCallback.onItemClicked(AppLevelConstants.PERMISSION_DENIED,-1);
//                    }
//                }
//            }, object : Consumer<Throwable> {
//                override fun accept(t: Throwable?) {
//                    showSnackBar(t?.getLocalizedMessage()?:resources.getString(R.string.msg_somthing_went_wrong));
//                    dataItemCallback.onItemClicked(AppLevelConstants.PERMISSION_DENIED,-1);
//                }
//            })
//    }

    protected open fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    protected abstract fun resume();
    protected abstract fun stop();
    protected abstract fun setUp();
}