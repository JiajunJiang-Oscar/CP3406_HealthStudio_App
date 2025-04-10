package com.example.healthstudio.data

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Float // 当前温度（单位：摄氏度）
)

data class Weather(
    val description: String // 天气描述（如 "clear sky", "rainy"）
)