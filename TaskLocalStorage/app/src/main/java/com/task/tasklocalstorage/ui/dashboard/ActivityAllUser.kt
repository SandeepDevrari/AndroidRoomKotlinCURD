package com.task.tasklocalstorage.ui.dashboard

import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.task.tasklocalstorage.R
import com.task.tasklocalstorage.databinding.LayoutActivityAllUserBinding
import com.task.tasklocalstorage.room.model.user.User
import com.task.tasklocalstorage.ui.dashboard.adapter.AllUserRecyclerAdapter
import com.task.tasklocalstorage.ui.dashboard.userdetail.ActivityUserDetail
import com.task.tasklocalstorage.ui.helper.base.BindingActivity
import com.task.tasklocalstorage.ui.helper.callback.DataItemListener
import com.task.tasklocalstorage.ui.helper.callback.OnViewClicked
import com.task.tasklocalstorage.utils.constant.AppLevelConstants
import java.util.*

class ActivityAllUser : BindingActivity<LayoutActivityAllUserBinding>(), OnViewClicked {

    lateinit var viewModel: AllUserViewModel

    lateinit var adapter: AllUserRecyclerAdapter

    override fun getLayout(): Int {
        return R.layout.layout_activity_all_user
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun setUp() {
        viewModel=getViewModel(AllUserViewModel::class.java)

        binding.viewHandler=this

        adapter= AllUserRecyclerAdapter(this, object : DataItemListener<User> {
            override fun onItem(t: User) {
                val intent= Intent(applicationContext,ActivityUserDetail::class.java)
                intent.putExtra(AppLevelConstants.PARCEL_DATA,t.userEmail)
                intent.putExtra(AppLevelConstants.PARCEL_EXTRA_DATA,t.userName)
                intent.putExtra(AppLevelConstants.EXTRA_DATA,t.userImage)

                startNewIntent(intent)
            }
        })

        binding.rvAllUser.adapter=adapter

        viewModel.getAllUsers().observe(this, object : Observer<List<User>> {
            override fun onChanged(t: List<User>?) {
                if(t!=null){
                    adapter.refreshUserList(t)

                    if(t!!.size>0){
                        binding.tvNoData.isVisible=false
                        binding.rvAllUser.isVisible=true
                    }else{
                        binding.tvNoData.isVisible=true
                        binding.rvAllUser.isVisible=false
                    }
                }else{
                    binding.tvNoData.isVisible=true
                    binding.rvAllUser.isVisible=false
                }
            }

        })
    }

    override fun onViewClicked(view: View) {
        when(view.id){
            R.id.btn_add_new_user->{
                startNewIntent(ActivityUserDetail::class.java)
            }
        }
    }
}