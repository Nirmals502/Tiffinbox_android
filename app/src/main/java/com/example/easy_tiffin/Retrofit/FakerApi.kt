package com.example.easy_tiffin.Retrofit

import com.example.easy_tiffin.Models.Products
import retrofit2.Response
import retrofit2.http.GET

interface FakerApi {
    @GET("products")
    suspend fun getProducts() :Response<List<Products>>
}