package com.example.healthstudio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [HealthData::class], version = 3, exportSchema = false)
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
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // DatabaseCallback**
    private class DatabaseCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            CoroutineScope(Dispatchers.IO).launch {
                val dao = getDatabase(context).healthDataDao()

                // Default health data
                dao.insertHealthData(HealthData(
                    "Walk Distance Today",
                    "--/--",
                    "health",
                    "km"
                ))
                dao.insertHealthData(HealthData(
                    "Heart Rate",
                    "--/--",
                    "health", "bpm"
                ))
                dao.insertHealthData(HealthData(
                    "Sleep Time",
                    "--/--",
                    "health",
                    "hour"
                ))
                dao.insertHealthData(HealthData(
                    "Psychological States",
                    "--/--",
                    "health",
                    "score (100pts)"
                ))
                dao.insertHealthData(HealthData(
                    "Weight",
                    "--/--",
                    "health",
                    "kg"
                ))
                dao.insertHealthData(HealthData(
                    "Height",
                    "--/--",
                    "health",
                    "cm"
                ))

                // Default fitness data
                dao.insertHealthData(HealthData(
                    "Fitness Record - Activity",
                    "--/--",
                    "fitness",
                    "calorie"
                ))
                dao.insertHealthData(HealthData(
                    "Fitness Record - Fitness",
                    "--/--",
                    "fitness",
                    "calorie"
                ))
                dao.insertHealthData(HealthData(
                    "Fitness Record - Stand",
                    "--/--",
                    "fitness",
                    "calorie"
                ))
                dao.insertHealthData(HealthData(
                    "Run time",
                    "--/--",
                    "fitness",
                    "min"
                ))
                dao.insertHealthData(HealthData(
                    "Cycling time (min)",
                    "--/--",
                    "fitness",
                    "min"
                ))
                dao.insertHealthData(HealthData(
                    "Swimming time (min)",
                    "--/--",
                    "fitness",
                    "min"
                ))
            }
        }
    }
}