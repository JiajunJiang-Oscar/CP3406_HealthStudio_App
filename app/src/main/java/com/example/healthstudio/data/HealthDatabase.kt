package com.example.healthstudio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [HealthData::class], version = 1, exportSchema = false)
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
                ).addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // ✅ **修正的数据库回调**
    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData("Steps / Distance", "--/--"))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData("Heart Rate", "--/--"))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData("Sleep Time", "--/--"))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData("Weight", "--/--"))
                INSTANCE?.healthDataDao()?.insertHealthData(HealthData("Height", "--/--"))
            }
        }
    }
}