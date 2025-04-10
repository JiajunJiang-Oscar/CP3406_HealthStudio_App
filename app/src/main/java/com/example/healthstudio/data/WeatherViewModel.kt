package com.example.healthstudio.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthstudio.network.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    private val weatherService: WeatherService

    companion object {
        private const val API_KEY = "85d6595b0e3d4149aeab9caa5308ebeb"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherService = retrofit.create(WeatherService::class.java)
    }

    fun fetchWeather(city: String, onResult: (WeatherResponse?) -> Unit) {
        weatherService.getCurrentWeather(city, API_KEY)
            .enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    Log.e(
                        "WeatherViewModel",
                        "API request unsuccessful: ${response.errorBody()?.string()}"
                    )
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(
                    "WeatherViewModel",
                    "An error occurred while requesting weather data: ${t.message}"
                )
                onResult(null)
            }
        })
    }
}