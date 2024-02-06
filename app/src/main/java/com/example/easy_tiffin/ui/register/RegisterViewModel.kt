package com.example.easy_tiffin.ui.register

// RegisterViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registrationResult = MutableLiveData<Pair<Boolean, String>>()
    val registrationResult: LiveData<Pair<Boolean, String>> get() = _registrationResult

    fun registerUser(name: String, phone: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                userRepository.registerUser(name, phone, email, password)
                _registrationResult.value = Pair(true, "Registration successful")
            } catch (e: Exception) {
                _registrationResult.value = Pair(false, "${e.message}")
            }
        }
    }
}