package com.example.TiffinBox.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.TiffinBox.Models.Products
import com.example.TiffinBox.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: ProductRepository) : ViewModel() {
    val productsLiveData: LiveData<List<Products>>
        get() = repository.products

    init {
        viewModelScope.launch { repository.getProducts() }
    }

}