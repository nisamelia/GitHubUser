package com.example.githubuser1.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser1.ui.follow_fragment.FollowersFragment
import com.example.githubuser1.ui.follow_fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, data: Bundle) :
    FragmentStateAdapter(activity) {

    private val fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
        }

        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }


}