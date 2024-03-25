package com.example.githubusersapi.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubusersapi.R
import com.example.githubusersapi.database.Favorite
import com.example.githubusersapi.databinding.ActivityDetail2Binding
import com.example.githubusersapi.ui.viewmodel.FavoriteAddUpdateViewModel
import com.example.githubusersapi.ui.viewmodel.FavoriteMainViewModel
import com.example.githubusersapi.ui.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val EXTRA_FAV = "extra_fav"
    }

    private lateinit var binding: ActivityDetail2Binding
    private lateinit var detailViewModel:DetailViewModel
    private lateinit var favoriteAddUpdateViewModel: FavoriteAddUpdateViewModel
    private lateinit var favoritemainViewModel: FavoriteMainViewModel

    private var isFavoriteAddedOrRemoved = false



    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("detail-debug","$binding")

        val viewModelFactory = ViewModelFactory(application)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        favoriteAddUpdateViewModel = ViewModelProvider(this).get(FavoriteAddUpdateViewModel::class.java)
        favoritemainViewModel = ViewModelProvider(this).get(FavoriteMainViewModel::class.java)

        val username = intent.getStringExtra("USERNAME")
        if (username != null){
            detailViewModel.getDetailUser(username)
        }

        favoritemainViewModel.getAllFavoriteUsers().observe(this){favorites->
            val currentUserName=username
            Log.d("currentUsername","$username")
            Log.d("intent-username","$")
            favorites?.forEach { favorite ->
                if (favorite.username == currentUserName){
                    binding?.fab?.setImageResource(R.drawable.baseline_favorite_24)
                }
            }
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

        binding?.fab?.setOnClickListener{
            saveFavorite()
            finish()

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

    private fun saveFavorite()
    {

        val username = intent.getStringExtra("USERNAME")
        val avatarUrl = detailViewModel.detailUser.value?.avatarUrl ?:""
        if(username != null){
            val favorite = Favorite(username,avatarUrl)
            favoriteAddUpdateViewModel.getFavoriteByUsername(username).observe(this){
                    existingFavorite->
                if(!isFavoriteAddedOrRemoved){
                    if ( existingFavorite == true){
                        favoriteAddUpdateViewModel.delete(favorite)
                        Toast.makeText(this, "Berhasil menghapus favorit", Toast.LENGTH_SHORT).show()
                        Log.d("hapus-favorit", "$existingFavorite")
                    }else{
                        favoriteAddUpdateViewModel.insertFavorite(favorite)

                        Toast.makeText(this, "Berhasil menambahkan favorit", Toast.LENGTH_SHORT).show()
                    }
                    isFavoriteAddedOrRemoved=true
                }

            }

        }else{
            Toast.makeText(this,"Gagal menambahkan favorit",Toast.LENGTH_SHORT).show()
        }
    }
}