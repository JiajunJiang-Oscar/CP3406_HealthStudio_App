package com.example.healthstudio.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthstudio.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HealthViewModel : ViewModel() {
    private val dao = HealthDatabase.getDatabase(App.instance).healthDataDao()

    private val _healthData = MutableStateFlow<List<HealthData>>(emptyList())
    val healthData: StateFlow<List<HealthData>> = _healthData.asStateFlow()

    init {
        loadHealthData("health")
        viewModelScope.launch {
            // Get all data in database
            val allData = dao.getAllHealthData()
            // Display all data
            println("All Database Data: $allData")

            loadHealthData("health")
        }
    }

    fun loadHealthData(category: String) {
        viewModelScope.launch {
            _healthData.value = dao.getHealthDataByCategory(category)
        }
    }

    fun updateHealthData(title: String, newValue: String) {
        viewModelScope.launch {
            val existingData = dao.getHealthDataByTitle(title)
            if (existingData != null) {
                val updatedData = existingData.copy(value = newValue)
                dao.updateHealthData(updatedData)

                // Reload database
                _healthData.value = dao.getAllHealthData()
            }
        }
    }

    fun refreshHealthData() {
        viewModelScope.launch {
            val updatedData = dao.getAllHealthData()
            // Use emit to let the UI listen for changes
            _healthData.emit(updatedData)
        }
    }
}