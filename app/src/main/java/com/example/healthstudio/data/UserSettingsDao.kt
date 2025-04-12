package com.example.healthstudio.data

import androidx.room.*

@Dao
interface UserSettingsDao {

    @Query("SELECT * FROM user_settings WHERE id = 1")
    suspend fun getUserSettings(): UserSettings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserSettings(userSettings: UserSettings)
}