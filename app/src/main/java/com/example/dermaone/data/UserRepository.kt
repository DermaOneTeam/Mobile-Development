package com.example.dermaone.data

import com.example.dermaone.api.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository(apiService: ApiService, private val userPreference: UserPreference) {

    suspend fun saveSession(user: UserStatus) {
        userPreference.saveSession(user)
    }
    fun getSession(): Flow<UserStatus> {
        return userPreference.getSession()
    }
    suspend fun getToken(): String {
        return userPreference.getToken()
    }
    suspend fun logout() {
        userPreference.logout()
    }
    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(apiService, userPreference)
                INSTANCE = instance
                instance
            }
        }
    }
}