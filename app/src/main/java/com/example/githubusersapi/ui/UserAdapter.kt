package com.example.githubusersapi.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersapi.R
import com.example.githubusersapi.data.response.UserItem
import com.example.githubusersapi.databinding.UserItemBinding

class UserAdapter:ListAdapter<UserItem,UserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener:((UserItem)->Unit)? =null

    fun setOnItemClickListener(listener:(UserItem)->Unit){
        onItemClickListener = listener
    }

    inner class UserViewHolder(private val binding:UserItemBinding):
            RecyclerView.ViewHolder(binding.root){
                init {
                    binding.root.setOnClickListener{
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION){
                            val user = getItem(position)
                            onItemClickListener?.invoke(user)
                        }
                    }
                }
            fun bind(user:UserItem){
                binding.tvItemName.text = "${user.login}"

                Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.profileImage)
                }
            }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user=getItem(position)
        holder.bind(user)

    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItem>(){
            override fun areItemsTheSame(
                oldItem: UserItem,
                newItem: UserItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserItem,
                newItem: UserItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}