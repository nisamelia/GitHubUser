package com.example.githubuser1.ui.follow_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser1.R
import com.example.githubuser1.databinding.FragmentFollowersBinding
import com.example.githubuser1.ui.detail.DetailActivity
import com.example.githubuser1.ui.detail.FollowersModel
import com.example.githubuser1.ui.adapter.UserAdapter

class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersModel
    private lateinit var followersAdapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arg = arguments
        username = arg?.getString(DetailActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowersBinding.bind(view)

        followersAdapter = UserAdapter()

        binding.apply {
            rvFollowers.setHasFixedSize(true)
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.adapter = followersAdapter
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowersModel::class.java]
        viewModel.setFollowers(username)
        viewModel.getFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                followersAdapter.submitList(it)
            } else {
                error("data gagal diambil")
            }
        }

        viewModel.isloading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}