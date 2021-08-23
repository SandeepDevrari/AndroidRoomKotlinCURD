package com.task.tasklocalstorage.ui.splash

import android.os.Handler
import android.os.Looper
import com.task.tasklocalstorage.R
import com.task.tasklocalstorage.databinding.ActivitySplashBinding
import com.task.tasklocalstorage.ui.helper.base.BindingActivity

class SplashActivity : BindingActivity<ActivitySplashBinding>(){
    private lateinit var viewModel: SplashViewModel

    override fun getLayout(): Int {
        hideSystemUI()
        return R.layout.activity_splash
    }

    override fun resume() {
        //
    }

    override fun stop() {
        //
    }

    override fun setUp() {
        viewModel = getViewModel(SplashViewModel::class.java)

        Handler(Looper.getMainLooper())
            .postDelayed({
                callHome()
            },4000)

    }
}