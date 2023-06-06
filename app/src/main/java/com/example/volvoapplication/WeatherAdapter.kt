package com.example.volvoapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter(private val cityWeatherList: List<CityWeather>) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityTextView: TextView = itemView.findViewById(R.id.city_text_view)
        val tempTextView: TextView = itemView.findViewById(R.id.temp_text_view)
        val weatherTextView: TextView = itemView.findViewById(R.id.weather_text_view)
        val windTextView: TextView = itemView.findViewById(R.id.wind_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cityWeather = cityWeatherList[position]
        holder.cityTextView.text = cityWeather.city
        holder.tempTextView.text = cityWeather.temp
        holder.weatherTextView.text = cityWeather.weather
        holder.windTextView.text = cityWeather.wind
    }

    override fun getItemCount(): Int {
        return cityWeatherList.size
    }
}
