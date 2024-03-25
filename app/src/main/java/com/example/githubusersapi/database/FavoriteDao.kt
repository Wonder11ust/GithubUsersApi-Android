package com.example.githubusersapi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insertFavorite(favorite:Favorite)
        @Delete
        fun delete(favorite: Favorite)

        @Query("SELECT * from favorite")
        fun getAllFavorites():LiveData<List<Favorite>>

        @Query("SELECT EXISTS(SELECT 1 FROM favorite WHERE username = :username )")
        fun getFavoriteByUsername(username:String):LiveData<Boolean>
}