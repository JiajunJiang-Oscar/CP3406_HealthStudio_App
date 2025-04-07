package com.example.healthstudio.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_data")
data class HealthData(
    // Title of the data
    @PrimaryKey val title: String,
    // Corresponding data
    val value: String,
    // Data category
    val category: String,
    // Unit category
    val unit: String
)