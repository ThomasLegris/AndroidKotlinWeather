package com.example.androidkotlinweather.fragments.adapter

import ViewPagerFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/// Adapter used to fill the view pager.
class ViewPagerAdapter(
    supportFragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragments: ArrayList<ViewPagerFragment>
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