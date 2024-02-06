package com.example.easy_tiffin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easy_tiffin.ui.stripe_setup.BankAccountRepository
import com.example.easy_tiffin.ui.stripe_setup.Connect_token_with_stripe
import javax.inject.Inject

class stripe_connect_factory@Inject constructor(private val repository: BankAccountRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Connect_token_with_stripe::class.java)) {
            return Connect_token_with_stripe(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
