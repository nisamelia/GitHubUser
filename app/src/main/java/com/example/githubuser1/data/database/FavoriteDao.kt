package com.example.githubuser1.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: FavoriteUser)

    @Update
    fun update(note: FavoriteUser)

    @Query("DELETE FROM user_fav WHERE user_fav.id = :id")
    fun delete(id: Int)

    @Query("SELECT * from user_fav")
    fun getAllNotes(): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM user_fav WHERE user_fav.id = :id)")
    fun isUserFavorited(id: Int): Int

}