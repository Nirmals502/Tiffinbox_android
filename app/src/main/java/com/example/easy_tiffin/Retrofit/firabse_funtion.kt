package com.example.easy_tiffin.Retrofit

import com.example.easy_tiffin.Models.AttachBankAccountRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface firabse_funtion {
    @POST("attachBankAccountToConnectAccount")
    suspend fun attachBankAccount(@Body request: AttachBankAccountRequest): Response<Void> // Change the response type if needed
}
