package com.task.tasklocalstorage.ui.dashboard.userdetail

import android.content.Context
import android.net.Uri
import android.view.View
import android.webkit.MimeTypeMap
import com.bumptech.glide.Glide
import com.task.tasklocalstorage.R
import com.task.tasklocalstorage.databinding.LayoutActivityUserDetailsBinding
import com.task.tasklocalstorage.ui.helper.base.BindingActivity
import com.task.tasklocalstorage.ui.helper.callback.DataItemTwoHandler
import com.task.tasklocalstorage.ui.helper.callback.OnViewClicked
import com.task.tasklocalstorage.utils.constant.AppLevelConstants
import com.task.tasklocalstorage.utils.exception.InputException
import com.task.tasklocalstorage.utils.util.openSingleImagePicker
import com.task.tasklocalstorage.utils.util.showSnackBar
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class ActivityUserDetail : BindingActivity<LayoutActivityUserDetailsBinding>(), OnViewClicked {

    lateinit var viewModel: UserDetailViewModel

    override fun getLayout(): Int {
        return R.layout.layout_activity_user_details
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun setUp() {
        viewModel=getViewModel(UserDetailViewModel::class.java)

        binding.viewHandler=this

        binding.data=viewModel.dataObserver

        getData()
    }

    private fun getData() {
        viewModel.dataObserver.userProfile=intent.getStringExtra(AppLevelConstants.EXTRA_DATA)?:""
        viewModel.dataObserver.userName=intent.getStringExtra(AppLevelConstants.PARCEL_EXTRA_DATA)?:""
        viewModel.dataObserver.userEmail=intent.getStringExtra(AppLevelConstants.PARCEL_DATA)?:""

        val file= File(viewModel.dataObserver.userProfile)
        if(!viewModel.dataObserver.userProfile.isNullOrEmpty() && file.exists()){

            Glide.with(this)
                .load(file)
                .into(binding.ivUserImage)
        }
    }

    override fun onViewClicked(view: View) {
        when(view.id){
            R.id.btn_add_update_user->{
                try {
                    viewModel.validateUserData()
                }catch (e:InputException){
                    showSnackBar(e.msg)
                }
            }

            R.id.iv_user_image->{
                openSingleImagePicker(object : DataItemTwoHandler<Uri?, Int> {
                    override fun onItemClicked(t: Uri?, r: Int) {
                        try {
                            t?.let {
                                val file: File = fileFromContentUri(this@ActivityUserDetail,it)

                                Glide.with(this@ActivityUserDetail)
                                    .load(file)
                                    .into(binding.ivUserImage)

                                viewModel.dataObserver.userProfile=file.absolutePath
                            }
                        }catch (e:Exception){
                            showSnackBar(resources.getString(R.string.msg_somthing_went_wrong))
                        }
                    }
                })
            }
        }
    }

    fun fileFromContentUri(context: Context, contentUri: Uri): File {
        // Preparing Temp file name
        val fileExtension = getFileExtension(context, contentUri)
        val fileName = "temp_file" + System.currentTimeMillis()+ if (fileExtension != null) ".$fileExtension" else ""

        // Creating Temp file
        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()

        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(contentUri)

            inputStream?.let {
                copy(inputStream, oStream)
            }

            oStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tempFile
    }

    private fun getFileExtension(context: Context, uri: Uri): String? {
        val fileType: String? = context.contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    }

    @Throws(java.io.IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }
}