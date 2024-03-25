package com.example.githubusersapi.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusersapi.database.FavoriteRepository
import com.example.githubusersapi.ui.DetailViewModel

class ViewModelFactory (private val mApplication:Application):ViewModelProvider.Factory{

//    private val favoriteRepository:FavoriteRepository by lazy {
//        FavoriteRepository(mApplication)
//    }
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(FavoriteMainViewModel::class.java)){
            return FavoriteMainViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(FavoriteAddUpdateViewModel::class.java)){
            return FavoriteAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}