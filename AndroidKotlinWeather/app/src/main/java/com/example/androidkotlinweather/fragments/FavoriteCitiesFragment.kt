package com.example.androidkotlinweather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidkotlinweather.R
import com.example.androidkotlinweather.fragments.common.CustomPagerFragment
import com.example.androidkotlinweather.persistance.FavoriteCity
import com.example.androidkotlinweather.persistance.CityDataBase

/// Show favorite cities list.
class FavoriteCitiesFragment(title: String) : CustomPagerFragment(title) {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
    }
}