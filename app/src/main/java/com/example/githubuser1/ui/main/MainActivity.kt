package com.example.githubuser1.ui.main

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser1.R
import com.example.githubuser1.data.response.GithubResponse
import com.example.githubuser1.data.response.ItemsItem
import com.example.githubuser1.data.retrofit.ApiConfig
import com.example.githubuser1.databinding.ActivityMainBinding
import com.example.githubuser1.ui.adapter.UserAdapter
import com.example.githubuser1.ui.favorite.ListFavActivity
import com.example.githubuser1.ui.settings.SettingPreferences
import com.example.githubuser1.ui.settings.SettingsViewModel
import com.example.githubuser1.ui.settings.ThemesActivity
import com.example.githubuser1.ui.settings.ViewModeFactory
import com.example.githubuser1.ui.settings.dataStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //tema di main activity
        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModeFactory(pref))[SettingsViewModel::class.java]
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvMain.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvMain.addItemDecoration(itemDecoration)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.user.observe(this){user ->
            setUserData(user)
        }

        mainViewModel.isloading.observe(this){isLoading ->
            if (isLoading) {
                binding.progress.visibility = View.VISIBLE
            } else {
                binding.progress.visibility = View.GONE
            }
        }

        mainViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                showError(it)
            }
        }

        with(binding){
            searchView.setupWithSearchBar(searchBar)
                searchView
                    .editText
                    .setOnEditorActionListener {
                            _, _, _ ->
                    searchBar.setText(searchView.text)
                        searchUser(searchView.text.toString())
                    searchView.hide()
                    false
                }

            searchBar.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                Toolbar.OnMenuItemClickListener,
                androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    return when (item.itemId) {
                        R.id.menu_fav -> {
                            startActivity(Intent(this@MainActivity, ListFavActivity::class.java))
                            true
                        }
                        R.id.menu_theme -> {
                            startActivity(Intent(this@MainActivity, ThemesActivity::class.java))
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }

    }

    private fun searchUser(q: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getListUsers(q)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(

                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responBody = response.body()
                    if (responBody?.items != null) {
                        setUserData(responBody.items)
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun setUserData(user: List<ItemsItem?>?){
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvMain.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {

            }
        })
    }

    private fun showError(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
}