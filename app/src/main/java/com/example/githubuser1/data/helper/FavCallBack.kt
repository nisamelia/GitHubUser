package com.example.githubuser1.data.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser1.data.database.FavoriteUser

class FavCallBack(
    private val oldFavList: List<FavoriteUser>,
    private val newFavList: List<FavoriteUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size
    override fun getNewListSize(): Int = newFavList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].id == newFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFav = oldFavList[oldItemPosition]
        val newFav = newFavList[newItemPosition]
        return oldFav.username == newFav.username && oldFav.avatarUrl == newFav.avatarUrl
    }
}