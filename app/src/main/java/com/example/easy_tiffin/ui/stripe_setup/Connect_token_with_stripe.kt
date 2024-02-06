package com.example.easy_tiffin.ui.stripe_setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easy_tiffin.Models.Payment_validations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel

class Connect_token_with_stripe @Inject constructor(private val firebaseFunction: BankAccountRepository) :
    ViewModel() {

    private val _tokenLiveData = MutableLiveData<Payment_validations>()
    val tokenLiveData: LiveData<Payment_validations> = _tokenLiveData


    fun attachBankAccount(tokenId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = firebaseFunction.attachBankAccount(tokenId,"acct_1Oa9LWG17HEZmTms")

            withContext(Dispatchers.Main) {
                result.onSuccess { validations ->
                    _tokenLiveData.value = validations
                }.onFailure { error ->
                    _tokenLiveData.value =
                        Payment_validations(error = error.message ?: "Unknown error")
                }

            }

        }
    }
}