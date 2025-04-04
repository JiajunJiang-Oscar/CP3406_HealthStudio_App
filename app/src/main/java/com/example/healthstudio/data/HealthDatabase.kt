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
                    .addCallback(DatabaseCallback(context))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // **修正 DatabaseCallback**
    private class DatabaseCallback(private val context: Context) : Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            CoroutineScope(Dispatchers.IO).launch {
                val dao = getDatabase(context).healthDataDao()

                // Default health data
                dao.insertHealthData(HealthData("Steps / Distance", "--/--", "health"))
                dao.insertHealthData(HealthData("Heart Rate", "--/--", "health"))
                dao.insertHealthData(HealthData("Sleep Time", "--/--", "health"))
                dao.insertHealthData(HealthData("Psychological States", "--/--", "health"))
                dao.insertHealthData(HealthData("Weight", "--/--", "health"))
                dao.insertHealthData(HealthData("Height", "--/--", "health"))

                // Default fitness data
                dao.insertHealthData(HealthData("Fitness Record - Activity", "--/--", "fitness"))
                dao.insertHealthData(HealthData("Fitness Record - Fitness", "--/--", "fitness"))
                dao.insertHealthData(HealthData("Fitness Record - Stand", "--/--", "fitness"))
                dao.insertHealthData(HealthData("Run time", "--/--", "fitness"))
                dao.insertHealthData(HealthData("Cycling time", "--/--", "fitness"))
                dao.insertHealthData(HealthData("Swimming time", "--/--", "fitness"))
            }
        }
    }
}