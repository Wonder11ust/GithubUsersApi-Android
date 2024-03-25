package com.example.githubusersapi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersapi.R
import com.example.githubusersapi.database.Favorite
import com.example.githubusersapi.databinding.ActivityFavoriteBinding
import com.example.githubusersapi.ui.viewmodel.FavoriteMainViewModel
import com.example.githubusersapi.ui.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private var _activityFavoriteBinding:ActivityFavoriteBinding?=null
    private val binding get() = _activityFavoriteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        favoriteAdapter = FavoriteAdapter()

        binding?.rvFavorites?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorites?.setHasFixedSize(true)
        binding?.rvFavorites?.adapter = favoriteAdapter

        val mainViewModel = obtainViewModel(this@FavoriteActivity)
        mainViewModel.getAllFavoriteUsers().observe(this){
            favorites->
            if (favorites != null){
                binding?.progressBar?.visibility =View.GONE
                favoriteAdapter.setListFavorites(favorites)
            }
            Log.d("List Favorites","$favorites")
        }


        favoriteAdapter.setOnItemClickListener { favorite->
            val intent = Intent(this@FavoriteActivity,DetailActivity::class.java)
            intent.putExtra("USERNAME",favorite.username)
            startActivity(intent)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding=null
    }

    private fun obtainViewModel(activity:AppCompatActivity):FavoriteMainViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory).get(FavoriteMainViewModel::class.java)
    }

    private fun showLoading(isLoading:Boolean){
        if (isLoading){
            binding?.progressBar?.visibility= View.VISIBLE
        }else{
            binding?.progressBar?.visibility = View.GONE
        }
    }
}