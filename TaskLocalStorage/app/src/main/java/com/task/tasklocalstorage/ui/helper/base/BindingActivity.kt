package com.task.tasklocalstorage.ui.helper.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingActivity<Bind:ViewDataBinding> : BaseActivity() {
    lateinit var binding:Bind

    @LayoutRes
    protected abstract fun getLayout():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,getLayout())

        binding.lifecycleOwner=this

        setUp()

    }
}