package com.example.androidkotlinweather.activities

import ViewPagerFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidkotlinweather.R
import com.example.androidkotlinweather.fragments.CurrentWeatherFragment
import com.example.androidkotlinweather.fragments.FavoriteCitiesFragment
import com.example.androidkotlinweather.fragments.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

/// Main entry of the app.
/// Screen used to search city by its name.
class MainActivity : AppCompatActivity() {
    /**
     * Override Funcs
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initViewPager()
    }

    /**
     * Private Funcs
     **/
    private fun initViewPager() {
        val fragmentsList: ArrayList<ViewPagerFragment> = arrayListOf(
            CurrentWeatherFragment(getString(R.string.current_weather_fragment_title)),
            FavoriteCitiesFragment(getString(R.string.cities_fragment_title))
        )

        view_pager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragmentsList)

        TabLayoutMediator(tabs, view_pager) { tab, position ->
            tab.text = fragmentsList[position].pageTitle
        }.attach()
    }

    private fun initView() {
        setSupportActionBar(toolbar)
    }
}