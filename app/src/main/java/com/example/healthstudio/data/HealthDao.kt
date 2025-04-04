package com.example.healthstudio.data

import androidx.room.*

@Dao
interface HealthDataDao {
    // Get all data
    @Query("SELECT * FROM health_data")
    suspend fun getAllHealthData(): List<HealthData>

    // Get the data by category（such as "health" or "fitness"）
    @Query("SELECT * FROM health_data WHERE category = :category")
    suspend fun getHealthDataByCategory(category: String): List<HealthData>

    // Insert data and replace if conflict occurs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthData(healthData: HealthData)

    // Update data
    @Update
    suspend fun updateHealthData(healthData: HealthData)

    // Delete data
    @Delete
    suspend fun deleteHealthData(healthData: HealthData)
}