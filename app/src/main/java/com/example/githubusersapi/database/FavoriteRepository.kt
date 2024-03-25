package com.example.githubusersapi.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {

    private val mFavoriteDao:FavoriteDao
    private val executorService:ExecutorService = Executors.newSingleThreadExecutor()

    init{
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }
     fun insertFavorite(favorite: Favorite){
        executorService.execute { mFavoriteDao.insertFavorite(favorite) }
    }

    fun deleteFavorite(favorite: Favorite){
        executorService.execute { mFavoriteDao.delete(favorite) }
    }

    fun getAllFavorites():LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun getFavoriteByUsername(username:String):LiveData<Boolean> = mFavoriteDao.getFavoriteByUsername(username)


}