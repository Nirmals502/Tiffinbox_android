package com.example.retrofi_implementation_.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofi_implementation_.Models.Products
import com.example.retrofi_implementation_.repository.ProductRepository
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