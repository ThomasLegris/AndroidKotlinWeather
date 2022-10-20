package com.example.androidkotlinweather.fragments

import ViewPagerFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidkotlinweather.R

/// Show favorite cities list.
class FavoriteCitiesFragment(title: String) : ViewPagerFragment(title) {
    /**
     * Override Funcs
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_cities, container, false)
    }
}