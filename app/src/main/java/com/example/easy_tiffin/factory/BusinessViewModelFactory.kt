package com.example.easy_tiffin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easy_tiffin.repository.BusinessRepository
import com.example.easy_tiffin.viewModel.BusinessViewModel

class BusinessViewModelFactory(private val repository: BusinessRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusinessViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusinessViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}