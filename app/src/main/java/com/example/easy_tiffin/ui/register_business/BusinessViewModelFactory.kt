package com.example.easy_tiffin.ui.register_business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BusinessViewModelFactory(private val repository: BusinessRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusinessViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusinessViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}