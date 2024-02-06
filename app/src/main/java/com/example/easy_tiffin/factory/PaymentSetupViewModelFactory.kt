package com.example.easy_tiffin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easy_tiffin.ui.stripe_setup.StripeService_repository
import com.example.easy_tiffin.ui.stripe_setup.PaymentSetupViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class PaymentSetupViewModelFactory @Inject constructor(private val repository: StripeService_repository) :
        ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(PaymentSetupViewModel::class.java)) {
                    return PaymentSetupViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
    }
}
