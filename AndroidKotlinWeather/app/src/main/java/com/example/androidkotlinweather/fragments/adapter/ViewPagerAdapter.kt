package com.example.androidkotlinweather.fragments.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidkotlinweather.fragments.common.CustomPagerFragment

/// Adapter used to fill the view pager.
class ViewPagerAdapter(
    supportFragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragments: ArrayList<CustomPagerFragment>
) :
    FragmentStateAdapter(supportFragmentManager, lifecycle) {

    /**
     * Override Funcs
     */
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}