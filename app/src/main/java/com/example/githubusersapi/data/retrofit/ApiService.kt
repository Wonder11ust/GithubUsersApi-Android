package com.example.githubusersapi.data.retrofit

import com.example.githubusersapi.data.response.DetailUserResponse
import com.example.githubusersapi.data.response.GithubResponse
import com.example.githubusersapi.data.response.ItemsItem
import com.example.githubusersapi.data.response.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
//    @Headers("Authorization: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")
    fun getUsers(
        @Query("q") login: String
    ): Call<GithubResponse>

    @GET("users/{username}")
   // @Headers("Authorization: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")
    fun getDetailUser(
       @Path("username") username:String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username:String):Call<List<UserItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username:String):Call<List<UserItem>>
}