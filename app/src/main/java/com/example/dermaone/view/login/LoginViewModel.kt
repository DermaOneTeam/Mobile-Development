package com.example.dermaone.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dermaone.data.UserRepository
import com.example.dermaone.data.UserStatus
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun saveSession(user: UserStatus) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
    fun setLoading(status: Boolean){
        _isLoading.value = status
    }
}