package com.example.githubuser1.data.retrofit

import com.example.githubuser1.BuildConfig
import com.example.githubuser1.data.response.DetailGithubResponse
import com.example.githubuser1.data.response.GithubResponse
import com.example.githubuser1.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //search
    @GET("search/users")
    @Headers("Authorization: token $MY_KEY")
    fun getListUsers(@Query("q") q: String): Call<GithubResponse>

    //detail
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailGithubResponse>

    //followers list
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    //following list
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

    companion object {
        const val MY_KEY = BuildConfig.KEY
    }
}