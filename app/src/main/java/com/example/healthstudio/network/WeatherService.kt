package com.example.healthstudio.network

import com.example.healthstudio.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeather(
        // CIty name
        @Query("q") city: String,
        // API Key
        @Query("appid") apiKey: String = "06984ea4210022fad9455cadaad62cf4",
        // Set the unit to Celsius
        @Query("units") units: String = "metric"
    ): Call<WeatherResponse>
}