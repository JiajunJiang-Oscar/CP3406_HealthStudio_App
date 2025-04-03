package com.example.healthstudio.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_data")
data class HealthData(
    @PrimaryKey val title: String,  // 数据的标题，如 "heartrate"
    val value: String               // 对应的数据，如 "--/--"
)