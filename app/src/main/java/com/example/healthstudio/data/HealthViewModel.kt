package com.example.healthstudio.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthstudio.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HealthViewModel(
    private val dao: HealthDataDao = HealthDatabase.getDatabase(App.instance).healthDataDao(),
    private val userDao: UserSettingsDao = HealthDatabase.getDatabase(App.instance).userSettingsDao()
) : ViewModel() {
    // Data value
    private val _healthData = MutableStateFlow<List<HealthData>>(emptyList())
    val healthData: StateFlow<List<HealthData>> = _healthData.asStateFlow()
    private val _userSettings = MutableStateFlow(UserSettings(username = "default", gender = "", age = 0))
    val userSettings: StateFlow<UserSettings> = _userSettings.asStateFlow()

    init {
        loadUserSettings()
        viewModelScope.launch {
            println("All Database Data: ${dao.getAllHealthData()}")
        }
    }

    fun loadData(category: String) {
        // function of load data
        viewModelScope.launch {
            _healthData.value = dao.getHealthDataByCategory(category)
        }
    }

    private fun loadUserSettings() {
        // Function of load user information
        viewModelScope.launch {
            val user = userDao.getUserSettings() ?: UserSettings(username = "Default", gender = "Unknown")
            _userSettings.value = user
        }
    }

    fun updateUsername(newName: String) {
        // Function of update user name
        viewModelScope.launch {
            val current = userDao.getUserSettings() ?: UserSettings(username = "default", gender = "Unknown")
            val updated = current.copy(username = newName)
            userDao.updateUserSettings(updated)
            _userSettings.value = updated
        }
    }

    fun updateGender(newGender: String) {
        // Function of update user gender
        viewModelScope.launch {
            val current = userDao.getUserSettings() ?: UserSettings(username = "default", gender = "Unknown")
            val updated = current.copy(gender = newGender)
            userDao.updateUserSettings(updated)
            _userSettings.value = updated
        }
    }

    fun updateAge(newAge: Int) {
        // Function of update user age
        viewModelScope.launch {
            val current = userDao.getUserSettings() ?: UserSettings(username = "default", gender = "Unknown")
            val updated = current.copy(age = newAge)
            userDao.updateUserSettings(updated)
            _userSettings.value = updated
        }
    }

    fun updateHealthData(title: String, newValue: String) {
        // Function of update user data
        viewModelScope.launch {
            val existingData = dao.getHealthDataByTitle(title)
            if (existingData != null) {
                val updatedData = existingData.copy(value = newValue)
                dao.updateHealthData(updatedData)
            }
        }
    }

    fun refreshData(category: String) {
        // Function of refresh user data
        viewModelScope.launch {
            val updatedData = dao.getHealthDataByCategory(category)
            // Use emit to let the UI listen for changes
            _healthData.emit(updatedData)
        }
    }
}