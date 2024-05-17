package com.example.githubuser1.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser1.data.database.FavoriteUser
import com.example.githubuser1.data.helper.FavCallBack
import com.example.githubuser1.databinding.ListUserBinding
import com.example.githubuser1.ui.detail.DetailActivity

class FavAdapter : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    private val listFav = ArrayList<FavoriteUser>()
    fun setListFav(listFav: List<FavoriteUser>) {
        val diffCallback = FavCallBack(this.listFav, listFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFav.clear()
        this.listFav.addAll(listFav)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFav.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(listFav[position])
    }

    inner class FavViewHolder(private val binding: ListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fav: FavoriteUser) {
            with(binding) {
                tvUser.text = fav.username
                Glide.with(binding.imgUser.context)
                    .load(fav.avatarUrl)
                    .into(binding.imgUser)
                cvUser.setOnClickListener { it ->
                    val intent = Intent(it.context, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_USERNAME, fav.username)
                        it.putExtra(DetailActivity.EXTRA_PICT, fav.avatarUrl)
                        it.putExtra(DetailActivity.EXTRA_ID, fav.id)
                    }
                    it.context.startActivity(intent)
                }
            }
        }
    }
}