package com.example.githubuser1.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser1.data.response.GithubResponse
import com.example.githubuser1.data.response.ItemsItem
import com.example.githubuser1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _user = MutableLiveData<List<ItemsItem?>?>()
    val user: LiveData<List<ItemsItem?>?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isloading : LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    companion object{
        private const val TAG = "MainViewModel"
        private const val USER = "amelia"
    }

    init {
        findUser()
    }

    private fun findUser(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers(USER)
        client.enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val users: List<ItemsItem?>? = response.body()?.items
                    if (users != null) {
                        _user.value = users
                    } else {
                        _errorMessage.value = "User Not Found"
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}

