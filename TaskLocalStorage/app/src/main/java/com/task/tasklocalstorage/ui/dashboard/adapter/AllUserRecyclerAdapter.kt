package com.task.tasklocalstorage.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.task.tasklocalstorage.R
import com.task.tasklocalstorage.databinding.LayoutViewUserInfoBinding
import com.task.tasklocalstorage.room.model.user.User
import com.task.tasklocalstorage.ui.helper.callback.DataItemListener
import com.task.tasklocalstorage.ui.helper.callback.OnViewClicked
import java.io.File
import java.lang.Exception

class AllUserRecyclerAdapter(val context:Context,
                             val dataItemListener: DataItemListener<User>)
    : RecyclerView.Adapter<AllUserRecyclerAdapter.HolderClass>(){

    var list: List<User>?=null

    inner class HolderClass(val binding: LayoutViewUserInfoBinding) : RecyclerView.ViewHolder(binding.root),
        OnViewClicked {
        fun bind(user: User) {

            binding.viewHandler=this

            binding.tvUserName.text=user
                .userName
            val file=File(user.userImage)

            if(file.exists()){
                Glide.with(context)
                    .load(file)
                    .into(binding.ivUserImage)
            }
        }

        override fun onViewClicked(view: View) {
            try {
                dataItemListener.onItem(list!![absoluteAdapterPosition])
            }catch (e:Exception){
                //todo
            }
        }

    }

    fun refreshUserList(list:List<User>){
        this.list=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        val binding:LayoutViewUserInfoBinding=DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.layout_view_user_info, parent,false)
        return HolderClass(binding)
    }

    override fun onBindViewHolder(holder: HolderClass, position: Int) {
        if(list!=null && list!!.size>position){
            holder.bind(list!![position])
        }
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }
}