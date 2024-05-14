package com.example.easy_tiffin.ui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MenuRepository) : ViewModel() {
    private val _menuItems = MutableLiveData<List<MenuItem>>()
    val menuItems: LiveData<List<MenuItem>> get() = _menuItems
    init {
        viewModelScope.launch {
            loadMenuItems()
        }
    }

    private suspend fun loadMenuItems() {
        _menuItems.value = repository.getMenuItems()
    }
}
