package com.example.githubuser1.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser1.BuildConfig
import com.example.githubuser1.R
import com.example.githubuser1.data.database.FavoriteUser
import com.example.githubuser1.databinding.ActivityDetailBinding
import com.example.githubuser1.ui.favorite.FavCrudViewModel
import com.example.githubuser1.ui.favorite.ViewModelFactory
import com.example.githubuser1.ui.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    private lateinit var factory: ViewModelFactory
    private val favViewModel: FavCrudViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getStringExtra(EXTRA_USERNAME)
        val avatarUrl = intent.getStringExtra(EXTRA_PICT)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        val bundle = Bundle()

        bundle.putString(EXTRA_USERNAME, user)

        viewModel.setDetail(user.toString())
        viewModel.getDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvProfilename.text = it.name
                    tvUname.text = it.login
                    tvFollowers.text = "${it.followers}\nFOLLOWERS"
                    tvFollowing.text = "${it.following}\nFOLLOWING"
                    Glide.with(binding.imgDetail.context)
                        .load(it.avatarUrl)
                        .into(binding.imgDetail)
                }
            }
        }
        viewModel.isloading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar2.visibility = View.VISIBLE
            } else {
                binding.progressBar2.visibility = View.GONE
            }
        }


        factory = ViewModelFactory.getInstance(application)

        var isFavorited = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = favViewModel.checkFav(id)
            withContext(Dispatchers.Main) {
                if (count > 0) {
                    binding.toggleButton.isChecked = true
                    isFavorited = true
                } else {
                    binding.toggleButton.isChecked = false
                    isFavorited = false
                }
            }
        }

        binding.btnShare.setOnClickListener {
            val url = BuildConfig.GITHUB
            val browse = Intent(Intent.ACTION_VIEW, Uri.parse(url + user))
            startActivity(browse)
        }

        binding.toggleButton.setOnClickListener {
            val entity = user?.let { FavoriteUser(id, user, avatarUrl.toString()) }
            isFavorited = !isFavorited
            if (isFavorited) {
                binding.toggleButton.isChecked = true
                favViewModel.insert(entity as FavoriteUser)
            } else {
                binding.toggleButton.isChecked = false
                favViewModel.delete(id)
            }
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_following,
            R.string.tab_followers
        )
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_PICT = "extra_pict"
        const val EXTRA_ID = "extra_id"
    }
}




