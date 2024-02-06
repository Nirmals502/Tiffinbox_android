package com.example.easy_tiffin.ui.stripe_setup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_tiffin.R
import com.example.easy_tiffin.Models.Payment_validations
import com.stripe.android.model.BankAccountTokenParams
import com.stripe.android.model.Token
import com.stripe.android.ApiResultCallback
import com.example.easy_tiffin.ui.stripe_setup.StripeService_repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentSetupViewModel @Inject constructor(private val stripeServiceRepository: StripeService_repository) :
    ViewModel() {

    private val _tokenLiveData = MutableLiveData<Payment_validations>()
    val tokenLiveData: LiveData<Payment_validations> = _tokenLiveData

    fun createBankAccountToken_(
        accountHolderName: String,
        accountNumber: String,
        transitNumber: String,
        branchNumber: String,
        context: Context
    ) {
        val stripeAccountId = context.getString(R.string.stripe_account_id)
        val idempotencyKey: String? = null

        val params = BankAccountTokenParams(
            accountNumber = accountNumber,
            country = "CA",
            currency = "CAD",
            accountHolderName = accountHolderName,
            accountHolderType = BankAccountTokenParams.Type.Individual,
            routingNumber = "$transitNumber-$branchNumber"
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                stripeServiceRepository.createBankAccountToken(params,
                    stripeAccountId,
                    idempotencyKey,
                    object : ApiResultCallback<Token> {
                        override fun onSuccess(result: Token) {
                            _tokenLiveData.value = Payment_validations(Success = result.id)
                            _tokenLiveData.value =
                                Payment_validations(bank_name = result.bankAccount?.bankName)
                            _tokenLiveData.value =
                                Payment_validations(last_4digit = result.bankAccount?.last4)

                        }

                        override fun equals(other: Any?): Boolean {
                            return super.equals(other)
                        }

                        override fun onError(e: Exception) {
                            _tokenLiveData.value = Payment_validations(error = e.toString())
                        }
                    })
            } catch (e: Exception) {
                _tokenLiveData.value = Payment_validations(error = e.toString())
            }
        }

    }


    fun Validations(
        accountHolderName: String,
        accountNumber: String,
        transitNumber: String,
        branchNumber: String
    ) {
        when {


            accountHolderName.isEmpty() -> _tokenLiveData.value =
                Payment_validations(acc_holder_name = R.string.accnt_holder_name)

            accountNumber.isEmpty() -> _tokenLiveData.value =
                Payment_validations(account_number = R.string.account_number_)

            accountNumber.length !in 7..12 -> _tokenLiveData.value =
                Payment_validations(account_number = R.string.account_number_length_error)


            transitNumber.isEmpty() -> _tokenLiveData.value =
                Payment_validations(transit_number = R.string.transit_number)

            transitNumber.length != 5 -> _tokenLiveData.value =
                Payment_validations(transit_number = R.string.transit_number_length_error)


            branchNumber.isEmpty() -> _tokenLiveData.value =
                Payment_validations(institute_number = R.string.institute_number_)

            branchNumber.length != 3 -> _tokenLiveData.value =
                Payment_validations(institute_number = R.string.institution_number_length_error)


            else -> {
                _tokenLiveData.value = Payment_validations(isDataValid = true)
            }
        }
    }
}