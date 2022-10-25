package com.example.androidkotlinweather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidkotlinweather.R
import com.example.androidkotlinweather.models.CityWeather

/// Adapter to fill a layout item with favorite city information.
class CityItemAdapter(
    private val items: MutableList<CityWeather>,
    private val onCardClickListener: OnClickListener
) : RecyclerView.Adapter<CityItemAdapter.ViewHolder>() {
    /**
     * ViewHolder
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.title_text)
        val cardView: CardView = itemView.findViewById(R.id.card_view)
        val descriptionText: TextView = itemView.findViewById(R.id.description_text)
        val rightIcon: ImageView = itemView.findViewById(R.id.right_icon)
        val rightText: TextView = itemView.findViewById(R.id.right_text)
    }

    /**
     * Override Funcs
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /// Inflate the view
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_city_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardView.setOnClickListener(onCardClickListener)
        val currentCityItem = items[position]
        holder.titleText.text = currentCityItem.name
        holder.descriptionText.text = currentCityItem.temp
        holder.cardView.tag = position
        holder.rightIcon.setImageResource(currentCityItem.imageRes)
        holder.rightText.text = currentCityItem.description
    }

    override fun getItemCount(): Int {
        return items.size
    }
}