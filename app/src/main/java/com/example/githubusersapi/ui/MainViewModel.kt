package com.example.githubusersapi.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersapi.data.response.GithubResponse
import com.example.githubusersapi.data.response.UserItem
import com.example.githubusersapi.data.retrofit.ApiConfig
import com.example.githubusersapi.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class MainViewModel:ViewModel() {

    private val _listUser = MutableLiveData<List<UserItem>>()
    val listUser:LiveData<List<UserItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val LOGIN = "arif"
    }

    init {
        getUsers(LOGIN)
    }

    @SuppressLint("SuspiciousIndentation")
     fun getUsers(username:String = LOGIN){
    _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(username)
        client.enqueue(object :Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody!=null){
                        _listUser.value = response.body()?.items
                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }



}