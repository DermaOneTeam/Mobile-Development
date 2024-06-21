package com.example.dermaone.data

import android.content.Context
import com.example.dermaone.api.ApiConfig
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getToken() }
        val apiService = ApiConfig.getApiService(user)
        return UserRepository.getInstance(apiService, pref)
    }
}