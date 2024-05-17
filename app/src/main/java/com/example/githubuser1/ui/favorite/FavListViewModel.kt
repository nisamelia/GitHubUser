package com.example.githubuser1.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser1.data.database.FavoriteUser

class FavListViewModel(application: Application) : ViewModel() {
    private val mFavRepository: FavRepository = FavRepository(application)
    fun getAllNotes(): LiveData<List<FavoriteUser>> = mFavRepository.getAllNotes()
}