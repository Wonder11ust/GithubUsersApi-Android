package com.example.githubusersapi.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubusersapi.database.Favorite
import com.example.githubusersapi.database.FavoriteRepository

class FavoriteAddUpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insertFavorite(favorite: Favorite){
        mFavoriteRepository.insertFavorite(favorite)
    }

    fun delete(favorite: Favorite){
        mFavoriteRepository.deleteFavorite(favorite)
    }

    fun getFavoriteByUsername(username:String):LiveData<Boolean>{
        return mFavoriteRepository.getFavoriteByUsername(username)
    }
}