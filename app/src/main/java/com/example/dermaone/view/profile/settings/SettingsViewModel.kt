package com.example.dermaone.view.profile.settings

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("settings", 0)
    private val _isDarkMode = MutableLiveData<Boolean>()

    val isDarkMode: LiveData<Boolean>
        get() = _isDarkMode

    init {
        _isDarkMode.value = prefs.getBoolean("dark_mode", false)
    }

    fun toggleDarkMode(isEnabled: Boolean) {
        _isDarkMode.value = isEnabled
        prefs.edit().putBoolean("dark_mode", isEnabled).apply()
        AppCompatDelegate.setDefaultNightMode(
            if (isEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}