package com.example.githubusersapi.data.response

import com.google.gson.annotations.SerializedName

data class UserItem (
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
)