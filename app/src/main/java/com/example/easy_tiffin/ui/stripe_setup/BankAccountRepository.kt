package com.example.easy_tiffin.ui.stripe_setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.easy_tiffin.Models.AttachBankAccountRequest
import com.example.easy_tiffin.Models.Payment_validations
import com.example.easy_tiffin.Retrofit.firabse_funtion
import javax.inject.Inject

class BankAccountRepository @Inject constructor(private val firebaseFunction: firabse_funtion) {

    private val _attachBankAccountResult = MutableLiveData<Result<Void>>() // Change the Result type if needed

    val attachBankAccountResult: LiveData<Result<Void>>
        get() = _attachBankAccountResult

    suspend fun attachBankAccount(tokenId: String, stripeAccountId: String): Result<Payment_validations> {
        return try {
            val request = AttachBankAccountRequest(bankAccountToken = tokenId, stripeAccountId = stripeAccountId)
            val response = firebaseFunction.attachBankAccount(request)

            if (response.isSuccessful && response.body() != null) {
                Result.success(Payment_validations(Success = response.body().toString())) // Adjust according to actual response
            } else {
                Result.failure(Exception("API call failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
