package com.example.healthstudio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [HealthData::class], version = 2, exportSchema = false)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun healthDataDao(): HealthDataDao

    companion object {
        @Volatile
        private var INSTANCE: HealthDatabase? = null

        fun getDatabase(context: Context): HealthDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthDatabase::class.java,
                    "health_database"
                )
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // Database callback
    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                // Default health data
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Steps / Distance",
                    "--/--",
                    "health"
                ))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Heart Rate",
                    "--/--",
                    "health"
                ))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Sleep Time",
                    "--/--",
                    "health"
                ))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Weight",
                    "--/--",
                    "health"
                ))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Height",
                    "--/--",
                    "health"
                ))

                // Default fitness data
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Fitness Record - Activity",
                    "--/--",
                    "fitness"
                ))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Fitness Record - Fitness",
                    "--/--",
                    "fitness"
                ))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Fitness Record - Stand",
                    "--/--",
                    "fitness"
                ))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Run time",
                    "--/--",
                    "fitness"
                ))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Cycling time",
                    "--/--",
                    "fitness"
                ))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData(
                    "Swimming time",
                    "--/--",
                    "fitness"
                ))
            }
        }
    }
}