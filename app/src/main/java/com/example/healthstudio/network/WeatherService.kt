package com.example.healthstudio.network

import com.example.healthstudio.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("q") city: String,     // 城市名
        @Query("appid") apiKey: String, // API Key
        @Query("units") units: String = "metric" // 设置单位为摄氏度
    ): Call<WeatherResponse>
}