package com.example.githubuser1.ui.follow_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser1.R
import com.example.githubuser1.databinding.FragmentFollowingBinding
import com.example.githubuser1.ui.detail.DetailActivity
import com.example.githubuser1.ui.detail.FollowingModel
import com.example.githubuser1.ui.adapter.UserAdapter

class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FollowingModel
    private lateinit var followingAdapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments
        username = arg?.getString(DetailActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowingBinding.bind(view)

        followingAdapter = UserAdapter()

        binding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = followingAdapter
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingModel::class.java]
        viewModel.setFollowing(username)
        viewModel.getFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                followingAdapter.submitList(it)
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