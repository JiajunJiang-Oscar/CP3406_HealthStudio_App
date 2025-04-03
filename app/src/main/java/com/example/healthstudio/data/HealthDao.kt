package com.example.healthstudio.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HealthDao {
    @Insert
    suspend fun insert(healthData: HealthData)

    @Query("SELECT * FROM health_data WHERE userId = :userId")
    suspend fun getHealthDataForUser(userId: Int): List<HealthData>
}