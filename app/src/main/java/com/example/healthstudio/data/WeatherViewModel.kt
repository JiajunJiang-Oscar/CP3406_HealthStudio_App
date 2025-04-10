package com.example.healthstudio.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthstudio.network.WeatherService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {
    private val weatherService: WeatherService

    // API key
    companion object {
        private const val API_KEY = "06984ea4210022fad9455cadaad62cf4"
    }

    init {
        // Example Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherService = retrofit.create(WeatherService::class.java)
    }

    fun fetchWeather(city: String, onResult: (WeatherResponse?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = weatherService.getCurrentWeather(city, API_KEY).execute()
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e("WeatherViewModel", "API request failed: ${response.errorBody()?.string()}")
                    onResult(null)
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "An error occurred while requesting weather data: ${e.message}")
                onResult(null)
            }
        }
    }
}