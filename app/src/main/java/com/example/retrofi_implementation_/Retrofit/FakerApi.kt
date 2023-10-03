package com.example.retrofi_implementation_.Retrofit

import com.example.retrofi_implementation_.Models.Products
import retrofit2.Response
import retrofit2.http.GET

interface FakerApi {
    @GET("products")
    suspend fun getProducts() :Response<List<Products>>
}