package com.example.githubuser1.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser1.databinding.ActivityListFavBinding
import com.example.githubuser1.ui.adapter.FavAdapter

class ListFavActivity : AppCompatActivity() {

    private var _activityFavBinding: ActivityListFavBinding? = null
    private val binding get() = _activityFavBinding
    private lateinit var favAdapter: FavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavBinding = ActivityListFavBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        showLoading()
        val mainViewModel = obtainViewModel(this@ListFavActivity)
        mainViewModel.getAllNotes().observe(this) { favList ->
            hideLoading()
            if (favList != null) {
                favAdapter.setListFav(favList)
            }
        }

        favAdapter = FavAdapter()
        binding?.rvFav?.layoutManager = LinearLayoutManager(this)
        binding?.rvFav?.setHasFixedSize(true)
        binding?.rvFav?.adapter = favAdapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavListViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavListViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavBinding = null
    }

    private fun showLoading() {
        binding?.progressBarFav?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding?.progressBarFav?.visibility = View.GONE
    }
}