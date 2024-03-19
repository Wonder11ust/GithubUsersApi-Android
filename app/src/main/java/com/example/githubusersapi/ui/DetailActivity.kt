package com.example.githubusersapi.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusersapi.R
import com.example.githubusersapi.databinding.ActivityDetail2Binding
import com.example.githubusersapi.databinding.ActivityDetailBinding
import com.example.githubusersapi.databinding.ActivityMenuBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetail2Binding
    private val detailViewModel by viewModels<DetailViewModel>()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)

     val username = intent.getStringExtra("USERNAME")
        if (username != null){
            detailViewModel.getDetailUser(username)
        }

        detailViewModel.detailUser.observe(this) { detailUser ->
            detailUser?.let {
                binding.tvUserName.text = it.login
                binding.tvName.text = it.name
                binding.tvFollowing.text = it.following.toString() + " following"
                binding.tvFollowers.text = it.followers.toString() + " followers"

                Glide.with(binding.root.context)
                    .load(it.avatarUrl)
                    .into(binding.profileImage)
            }
        }

        detailViewModel.isLoading.observe(this){
            isLoading->showLoading(isLoading)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(username,this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(
                when (position) {
                    0 -> R.string.tab_text_1
                    1 -> R.string.tab_text_2
                    else -> throw IllegalArgumentException("Invalid position: $position")
                }
            )
        }.attach()
    }

    private fun showLoading(isLoading:Boolean){
        if (isLoading){
            binding.progressBar.visibility= View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }


}