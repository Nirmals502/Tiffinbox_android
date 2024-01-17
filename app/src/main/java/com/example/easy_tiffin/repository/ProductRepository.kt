package com.example.easy_tiffin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.easy_tiffin.Models.Products
import com.example.easy_tiffin.Retrofit.FakerApi
import javax.inject.Inject

class ProductRepository @Inject constructor(private val fakerApi: FakerApi) {

    private val _products = MutableLiveData<List<Products>>()
    val products: LiveData<List<Products>>
        get() = _products

    suspend fun getProducts() {
        val result = fakerApi.getProducts()
        if (result.isSuccessful && result.body()!= null){
            _products.postValue(result.body())
        }
    }
}