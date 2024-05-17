package com.example.githubuser1.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser1.data.response.ItemsItem
import com.example.githubuser1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingModel : ViewModel() {
    private val _detail = MutableLiveData<List<ItemsItem>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isLoading

    fun setFollowing(login: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowing(login)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
            }

        })
    }

    fun getFollowing(): LiveData<List<ItemsItem>> {
        return _detail
    }
}