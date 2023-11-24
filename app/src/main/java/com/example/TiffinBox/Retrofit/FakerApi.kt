package com.example.TiffinBox.Retrofit

import com.example.TiffinBox.Models.Products
import retrofit2.Response
import retrofit2.http.GET

interface FakerApi {
    @GET("products")
    suspend fun getProducts() :Response<List<Products>>
}