package com.example.githubuser1.ui.detail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser1.data.response.DetailGithubResponse
import com.example.githubuser1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detail = MutableLiveData<DetailGithubResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isLoading

    fun setDetail(login: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(login)
        client.enqueue(object : Callback<DetailGithubResponse> {
            override fun onResponse(
                call: Call<DetailGithubResponse>,
                response: Response<DetailGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailGithubResponse>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
            }

        })
    }

    fun getDetail(): LiveData<DetailGithubResponse> {
        return _detail
    }


}