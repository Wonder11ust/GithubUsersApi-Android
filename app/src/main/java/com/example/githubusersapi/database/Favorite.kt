package com.example.githubusersapi.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    var username:String ="",
    var avatarUrl:String?=""
):Parcelable