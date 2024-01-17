package com.example.easy_tiffin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easy_tiffin.Models.Products
import com.example.easy_tiffin.repository.ProductRepository
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