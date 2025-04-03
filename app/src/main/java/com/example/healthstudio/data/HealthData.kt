package com.example.healthstudio.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_data")
data class HealthData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,   // 类别，如 "Steps", "Heart Rate" 等
    val value: String,      // 对应类别的数据值
    val userId: Int = 1     // 用于标识用户（默认只服务一个用户，可以是固定值1）
)