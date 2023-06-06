package com.example.volvoapplication

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : Activity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        requestWeather()
    }

    private fun requestWeather() {
        val cities = listOf(
            "北京",
            "上海",
            "广州",
            "深圳",
            "苏州",
            "沈阳"
        )
        val url =
            "https://restapi.amap.com/v3/weather/weatherInfo?key=your amap key&extensions=all&"

        val client = OkHttpClient()
        val requestList = mutableListOf<Request>()

        for (city in cities) {
            val request = Request.Builder().url(url + "city=$city").build()
            requestList.add(request)
        }

        val callList = mutableListOf<Call>()
        val cityWeatherList = mutableListOf<CityWeather>()

        for (request in requestList) {
            val call = client.newCall(request)
            callList.add(call)
        }

        for (i in callList.indices) {
            val response = callList[i].execute()
            val responseData = response.body?.string()

            responseData?.let {
                val jsonObj = JSONObject(it)
                val jsonArray = jsonObj.getJSONArray("forecasts")
                val cityJson = jsonArray.getJSONObject(0).getJSONObject("city")
                val castJsonArray = jsonArray.getJSONObject(0).getJSONArray("casts")
                val tomorrowCastJson = castJsonArray.getJSONObject(1)

                val city = cityJson.getString("name")
                val temp = tomorrowCastJson.getString("daytemp")
                val weather = tomorrowCastJson.getString("dayweather")
                val wind = tomorrowCastJson.getString("daywind")

                val cityWeather = CityWeather(city, temp, weather, wind)
                cityWeatherList.add(cityWeather)
            }
        }

        runOnUiThread {
            adapter = WeatherAdapter(cityWeatherList)
            recyclerView.adapter = adapter
        }
    }

}