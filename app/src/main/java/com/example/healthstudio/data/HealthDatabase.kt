package com.example.healthstudio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HealthData::class], version = 1)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun healthDao(): HealthDao

    companion object {
        @Volatile
        private var INSTANCE: HealthDatabase? = null

        fun getDatabase(context: Context): HealthDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthDatabase::class.java,
                    "health_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}