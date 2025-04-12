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

    private val userDao = HealthDatabase.getDatabase(App.instance).userSettingsDao()

    private val _username = MutableStateFlow("default")
    val username: StateFlow<String> = _username.asStateFlow()

    init {
        loadData("health")
        loadUsername()

        viewModelScope.launch {
            println("All Database Data: ${dao.getAllHealthData()}")
        }
    }

    private fun loadUsername() {
        viewModelScope.launch {
            val user = userDao.getUserSettings()
            _username.value = user?.username ?: "default"
        }
    }

    fun updateUsername(newName: String) {
        viewModelScope.launch {
            val current = userDao.getUserSettings() ?: UserSettings(username = "default")
            val updated = current.copy(username = newName)
            userDao.updateUserSettings(updated)
            _username.value = updated.username
        }
    }

    fun loadData(category: String) {
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
            }
        }
    }

    fun refreshData(category: String) {
        viewModelScope.launch {
            val updatedData = dao.getHealthDataByCategory(category)
            // Use emit to let the UI listen for changes
            _healthData.emit(updatedData)
        }
    }
}