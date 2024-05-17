package com.example.easy_tiffin.ui.ui.home.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easy_tiffin.ui.ui.home.repository.MenuRepository
import com.example.easy_tiffin.ui.ui.home.view_model.HomeViewModel

class ViewModelFactory(private val repository: MenuRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}