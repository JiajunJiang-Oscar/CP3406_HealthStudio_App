package com.example.healthstudio.data

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    // Current temperature (unit: Celsius)
    val temp: Float
)

data class Weather(
    // Weather description (e.g. "clear sky", "rainy")
    val description: String
)