package com.example.healthstudio.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val id: Int = 1,
    // User name
    val username: String,
    // User gender
    val gender: String = "Unknown",
    // User age
    val age: Int = 0
)