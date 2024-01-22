package com.example.easytiffin.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.easy_tiffin.R
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.Token
import com.stripe.android.view.CardInputWidget

class PaymentSetupActivity : AppCompatActivity() {

    private lateinit var stripe: Stripe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_setup)

        // Initialize the Stripe instance with your publishable key
        PaymentConfiguration.init(applicationContext, "your_publishable_key")
        stripe = Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey)

        // Handle button click to submit bank details
        findViewById<Button>(R.id.buttonSubmitBankDetails).setOnClickListener {
            submitBankDetails()
        }
    }

    private fun submitBankDetails() {
        // Get bank details from UI
        val bankName = findViewById<EditText>(R.id.editTextBankName).text.toString()
        val accountNumber = findViewById<EditText>(R.id.editTextAccountNumber).text.toString()

        // Create a Token with bank account details
        val bankAccountParams = AccountCreateParams.create(
            accountNumber = accountNumber,
            country = "CA",
            currency = "CAD",
            accountHolderName = "Account Holder Name",
            accountHolderType = BankAccountParams.AccountHolderType.Individual,
            routingNumber = "11000"  // Include if applicable in Canada
        )

        // Create a token for the bank account
        stripe.createBankAccountToken(
            bankAccountParams,
            null,
            object : ApiResultCallback<Token> {
                override fun onSuccess(result: Token) {
                    // Handle successful token creation
                    val token = result.id
                    // Send the token to your server for further processing
                    // (This part is typically done on the server side to avoid exposing your secret key)
                }

                override fun onError(e: Exception) {
                    // Handle error
                    e.printStackTrace()
                }
            }
        )
    }
}