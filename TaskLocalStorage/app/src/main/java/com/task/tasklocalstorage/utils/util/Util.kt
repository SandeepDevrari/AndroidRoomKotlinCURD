package com.task.tasklocalstorage.utils.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.*
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.gun0912.tedpermission.TedPermissionResult
import com.task.tasklocalstorage.R
import com.task.tasklocalstorage.databinding.LayoutProgressBarBinding
import com.task.tasklocalstorage.ui.helper.callback.DataItemListener
import com.task.tasklocalstorage.ui.helper.callback.DataItemTwoHandler
import com.task.tasklocalstorage.utils.constant.AppLevelConstants
import com.tedpark.tedpermission.rx2.TedRx2Permission
import gun0912.tedimagepicker.builder.TedRxImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import io.reactivex.functions.Consumer
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.regex.Pattern

fun Context.checkInternet() : Boolean{
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null) {
                return networkInfo.isConnected
            }
        } else {
            val network = connectivityManager.activeNetwork
            if (network != null) {
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(network)
                if (networkCapabilities != null) {
                    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    )
                }
            }
        }
    }
    return false
}

fun isValidEmail(email: String?): Boolean {
    val EMAIL_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    return email != null && !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches()
}

fun isMobileNumberValid(mobileNumber: String?): Boolean {
    val MOBILE_PATTERN = Pattern.compile("\\d{7,13}")
    return mobileNumber != null && !mobileNumber.isEmpty() && MOBILE_PATTERN.matcher(
        mobileNumber
    ).matches()
}

fun isPasswordValid(password: String?): Boolean {
    val PASSWORD =
        Pattern.compile(AppLevelConstants.PASSWORD_PATTERN)
    return password != null && !password.isEmpty() && PASSWORD.matcher(password).matches()
}

fun Context.hideKeyboard(view: View) {
    val imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun share(msg: String): Intent {
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
    sendIntent.type = "text/plain"
    return sendIntent
}

fun downloadFile(fileUrl: String, directory: File) {
    try {
        val url = URL(fileUrl)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        //urlConnection.setRequestMethod("GET");
        //urlConnection.setDoOutput(true);
        urlConnection.connect()
        val inputStream: InputStream = urlConnection.getInputStream()
        val fileOutputStream = FileOutputStream(directory)
        val totalSize: Int = urlConnection.getContentLength()
        val buffer = ByteArray(1024 * 1024)
        var bufferLength = 0
        while (inputStream.read(buffer).also({ bufferLength = it }) > 0) {
            fileOutputStream.write(buffer, 0, bufferLength)
        }
        fileOutputStream.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: MalformedURLException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun Context.registerInternetConnection(
    connectivity: DataItemListener<Boolean>
) {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    connectivity.onItem(true)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    connectivity.onItem(false)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                }
            })
        } else {
            val builder = NetworkRequest.Builder()
            builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            val networkRequest = builder.build()
            connectivityManager.registerNetworkCallback(
                networkRequest,
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        connectivity.onItem(true)
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        connectivity.onItem(false)
                    }
                })
        }
    } else {
        connectivity.onItem(false)
    }
}

/**
 * Method used to hide keyboard
 * @param view
 * @param activity
 */
fun Context.setupUI(view: View) {

    // Set up touch listener for non-text box views to hide keyboard.
    if (view !is EditText) {
        view.setOnTouchListener { v, event ->
            hideSoftKeyboard()
            false
        }
    }

    //If a layout container, iterate over children and seed recursion.
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            setupUI(innerView)
        }
    }
}

/**
 * Hide Keyboard
 * @param activity
 */
fun Context.hideSoftKeyboard() {
    val inputMethodManager =
        this.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
    var view = (this as Activity).currentFocus
    if (view == null) {
        view = View(this)
    }
    inputMethodManager.hideSoftInputFromWindow(
        view.windowToken, 0
    )
}

fun Context.showSnackBar(msg:String){
    Snackbar.make(
        (this as Activity).findViewById(android.R.id.content),
        msg,
        Snackbar.LENGTH_SHORT)
        .show()
}

private var customDialog: AlertDialog? =null

fun Context.showProgressBar(){
    hideCustomDialog()
    val progressBuilder= AlertDialog.Builder(this)
    val binding= LayoutProgressBarBinding.inflate(LayoutInflater.from(this),null,false)
    progressBuilder.setView(binding.root)
    progressBuilder.setCancelable(false)
    customDialog=progressBuilder.create()
    customDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    customDialog!!.show()
}

fun Context.hideCustomDialog(){
    if(customDialog!=null){
        customDialog!!.dismiss()
        customDialog=null
    }
}

@SuppressLint("CheckResult")
fun Context.checkThePermission(
    title: String,
    deniedMsg: String,
    dataItemCallback: DataItemTwoHandler<Int, Int>,
    vararg permissions: String
) {
    TedRx2Permission.with(this)
        .setRationaleTitle(title)
        .setRationaleMessage(deniedMsg)
        .setPermissions(*permissions)
        .setGotoSettingButton(true)
        .setGotoSettingButtonText(resources.getString(R.string.text_open_setting))
        .request()
        .subscribe(object : Consumer<TedPermissionResult> {
            override fun accept(t: TedPermissionResult?) {
                if (t?.isGranted() == true) {
                    dataItemCallback.onItemClicked(AppLevelConstants.PERMISSION_ACCEPTED,-1);
                } else {
                    dataItemCallback.onItemClicked(AppLevelConstants.PERMISSION_DENIED,-1);
                }
            }
        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable?) {
                showSnackBar(t?.getLocalizedMessage()?:resources.getString(R.string.msg_somthing_went_wrong));
                dataItemCallback.onItemClicked(AppLevelConstants.PERMISSION_DENIED,-1);
            }
        })
}

fun Context.openSingleImagePicker(dataItemCallback: DataItemTwoHandler<Uri?, Int>) {
    checkThePermission(
        resources.getString(R.string.text_accept_permission),
        resources.getString(R.string.msg_please_allow_us_to_use_permission),
        object : DataItemTwoHandler<Int, Int> {
            @SuppressLint("CheckResult")
            override fun onItemClicked(result: Int, r: Int) {
                if (result == AppLevelConstants.PERMISSION_ACCEPTED) {
                    TedRxImagePicker.with(this@openSingleImagePicker)
                        .mediaType(MediaType.IMAGE)
                        .cameraTileImage(R.drawable.ic_camera)
                        .cameraTileBackground(R.color.white)
                        .showCameraTile(true)
                        .showTitle(true)
                        .title(resources.getString(R.string.text_select_image))
                        .backButton(R.drawable.ic_back)
                        .start()
                        .subscribe(
                            { uri -> dataItemCallback.onItemClicked(uri, 0) }
                        ) { throwable ->
                            showSnackBar(throwable.localizedMessage)
                            dataItemCallback.onItemClicked(null, -1)
                        }
                } else {
                    showSnackBar(resources.getString(R.string.msg_image_permission_not_granted))
                    dataItemCallback.onItemClicked(null, -1)
                }
            }
        },
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}

fun Context.openEmail(email:String){
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_EMAIL, email)

    startActivity(Intent.createChooser(intent, "Send Email"))
}

fun Context.openWhatsApp(mobile:String){
    val url = "https://api.whatsapp.com/send?phone=$mobile"
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    startActivity(i)
}

enum class Status{
    SUCCESS,
    FAILED,
    PROCESSING
}
