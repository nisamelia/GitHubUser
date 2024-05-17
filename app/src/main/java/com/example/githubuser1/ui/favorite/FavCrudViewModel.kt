package com.example.githubuser1.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuser1.data.database.FavoriteUser

class FavCrudViewModel(application: Application) : ViewModel() {
    private var favRepository: FavRepository = FavRepository(application)

    fun insert(note: FavoriteUser) {
        favRepository.insert(note)
    }

    fun delete(id: Int) {
        favRepository.delete(id)
    }

    fun checkFav(id: Int) = favRepository.isFavorite(id)
}