package com.example.githubuser1.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser1.data.database.FavoriteDao
import com.example.githubuser1.data.database.FavoriteUser
import com.example.githubuser1.data.database.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository(application: Application) {
    private val mNotesDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun getAllNotes(): LiveData<List<FavoriteUser>> = mNotesDao.getAllNotes()

    fun insert(note: FavoriteUser) {
        executorService.execute { mNotesDao.insert(note) }
    }

    fun delete(id: Int) {
        executorService.execute { mNotesDao.delete(id) }
    }

    fun isFavorite(id: Int) = mNotesDao.isUserFavorited(id)
}