package com.example.easy_tiffin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.easy_tiffin.Models.Products
import com.example.easy_tiffin.Retrofit.firabse_funtion
import javax.inject.Inject

class ProductRepository @Inject constructor(private val function: firabse_funtion) {

    private val _products = MutableLiveData<List<Products>>()
    val products: LiveData<List<Products>>
        get() = _products

    suspend fun getProducts() {
//        val result = function.getProducts()
//        if (result.isSuccessful && result.body()!= null){
//            _products.postValue(result.body())
//        }
    }
}