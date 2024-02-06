package com.example.easy_tiffin.ui.stripe_setup

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.easy_tiffin.R
import com.example.easy_tiffin.Models.Payment_validations
import com.example.easy_tiffin.Progress_bar.ProgressBarHandler
import com.example.easy_tiffin.factory.PaymentSetupViewModelFactory
import com.example.easy_tiffin.factory.stripe_connect_factory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint // Add this annotation

class PaymentSetupActivity : AppCompatActivity() {

    private lateinit var viewModel: PaymentSetupViewModel
    private lateinit var viewModel_connect_with_stripe: Connect_token_with_stripe

    lateinit var accountHolderName: String
    lateinit var accountNumber: String
    lateinit var transitNumber: String
    lateinit var branchNumber: String
    private lateinit var progressBarHandler: ProgressBarHandler

    @Inject
    lateinit var repository: StripeService_repository

    @Inject
    lateinit var repository_connect_with_stripe: BankAccountRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paymentsetup)
        viewModel = ViewModelProvider(this, PaymentSetupViewModelFactory(repository))
            .get(PaymentSetupViewModel::class.java)
        viewModel_connect_with_stripe =
            ViewModelProvider(this, stripe_connect_factory(repository_connect_with_stripe))
                .get(Connect_token_with_stripe::class.java)

        progressBarHandler = ProgressBarHandler(this)


        // Initialize views and button click listener
        val accountHolderEditText = findViewById<EditText>(R.id.editTextAccountHolderName)
        val accountNumberEditText = findViewById<EditText>(R.id.editTextAccountNumber)
        val transitNumberEditText = findViewById<EditText>(R.id.transit_number)
        val branchNumberEditText = findViewById<EditText>(R.id.institute_number)
        val registerButton = findViewById<Button>(R.id.buttonRegisterBankDetails)

        registerButton.setOnClickListener {
            progressBarHandler.showLoading(true)
            registerButton.setText("")
            accountHolderName = accountHolderEditText.text.toString()
            accountNumber = accountNumberEditText.text.toString()
            transitNumber = transitNumberEditText.text.toString()
            branchNumber = branchNumberEditText.text.toString()

            viewModel.Validations(
                accountHolderName,
                accountNumber,
                transitNumber,
                branchNumber
            )
        }

        viewModel_connect_with_stripe.tokenLiveData.observe(this) { tokenLiveData ->
            progressBarHandler.showLoading(false)
            registerButton.setText("Register Bank Details")
            if (tokenLiveData.Success != null) {
                Log.d("stripe_connect......", tokenLiveData.Success)

                // Possibly reset validation state here if needed
            } else if (tokenLiveData.error != null) {

                Log.d("stripe_connect_error......", tokenLiveData.error)

                // Possibly reset validation state here if needed
            } else {
                // Handle validation errors and reset state for next round
                //handleValidationErrors(tokenLiveData)
            }

        }

        //  Observe tokenLiveData for successful token creation
        viewModel.tokenLiveData.observe(this) { tokenLiveData ->
            progressBarHandler.showLoading(false)
            registerButton.setText("Register Bank Details")

            // Check for success or error first
            if (tokenLiveData.Success != null) {
                Log.d("token......", tokenLiveData.Success)
                Log.d("Bankname......", tokenLiveData.bank_name.toString())
                Log.d("last4digit......", tokenLiveData.last_4digit.toString())
                progressBarHandler.showLoading(true)
                registerButton.setText("")
                viewModel_connect_with_stripe.attachBankAccount(tokenLiveData.Success)
                // Possibly reset validation state here if needed
            } else if (tokenLiveData.error != null) {
                Log.d("token......", tokenLiveData.error)
                if (tokenLiveData.error?.contains("routing number") == true) {
                    // This is a simplified check, adjust logic as necessary for accuracy
                    runOnUiThread {
                        transitNumberEditText.error =
                            "The transit number does not correspond with a recognized bank. Please use a valid transit number."
                        branchNumberEditText.error =
                            "The Branch number does not correspond with a recognized bank. Please use a valid Branch number."

                    }
                }
                // Possibly reset validation state here if needed
            } else {
                // Handle validation errors and reset state for next round
                handleValidationErrors(tokenLiveData)
            }
        }
    }

    private fun handleValidationErrors(tokenLiveData: Payment_validations) {
        // Reset all errors to null for next validation round
        resetValidationErrors()

        // Now set the errors based on current validation results

        tokenLiveData.acc_holder_name?.let {
            findViewById<EditText>(R.id.editTextAccountHolderName).error = getString(it)
        }
        tokenLiveData.account_number?.let {
            findViewById<EditText>(R.id.editTextAccountNumber).error = getString(it)
        }
        tokenLiveData.account_number?.let {
            findViewById<EditText>(R.id.editTextAccountNumber).error = getString(it)
        }
        tokenLiveData.institute_number?.let {
            findViewById<EditText>(R.id.institute_number).error =
                getString(it) // Assuming this is institute number's EditText
        }
        tokenLiveData.transit_number?.let {
            findViewById<EditText>(R.id.transit_number).error = getString(it)
        }

        if (tokenLiveData.isDataValid) {
            viewModel.createBankAccountToken_(
                accountHolderName,
                accountNumber,
                transitNumber,
                branchNumber, this
            )
        }
    }

    private fun resetValidationErrors() {
        // Clear errors for all fields
        findViewById<EditText>(R.id.editTextAccountHolderName).error = null
        findViewById<EditText>(R.id.editTextAccountNumber).error = null
        findViewById<EditText>(R.id.institute_number).error = null
        findViewById<EditText>(R.id.transit_number).error =
            null // Assuming this is the branch number's EditText
    }
}
