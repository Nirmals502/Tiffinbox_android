package com.example.easy_tiffin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.easy_tiffin.R
import com.example.easy_tiffin.data.BusinessDetails
import com.example.easy_tiffin.factory.BusinessViewModelFactory
import com.example.easy_tiffin.repository.BusinessRepository
import com.example.easy_tiffin.viewModel.BusinessViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Register_business : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: BusinessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_business)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        val businessRepository = BusinessRepository(auth, FirebaseFirestore.getInstance())
        viewModel = ViewModelProvider(this, BusinessViewModelFactory(businessRepository))
            .get(BusinessViewModel::class.java)

        val buttonRegisterBusiness: Button = findViewById(R.id.buttonRegisterBusiness)
        val editTextBusinessName: EditText = findViewById(R.id.editTextBusinessName)
        val editTextBusinessAddress: AutoCompleteTextView =
            findViewById(R.id.autoCompleteTextViewStreet)
        val editTextGST: EditText = findViewById(R.id.editTextGST)

        buttonRegisterBusiness.setOnClickListener {
            val businessName = editTextBusinessName.text.toString().trim()
            val businessAddress = editTextBusinessAddress.text.toString().trim()
            val gst = editTextGST.text.toString().trim()
            // val Userid = editTextGST.text.toString().trim()

            if (businessName.isEmpty() || businessAddress.isEmpty() || gst.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // You can add additional validations as needed

            val businessDetails = BusinessDetails(
                businessName = businessName,
                Street_address = "String",
                city = "String",
                state = "String",
                Postal_code = "String",
                Country = "String",
                gst = gst,
                Userid = "123456",
                createdAt = Timestamp.now().toString(),
                updatedAt = Timestamp.now().toString(),
                status = "Active" ,
                Phone = "9914564058"
            )

            viewModel.addBusinessDetails(businessDetails)
        }

        viewModel.businessRegistrationResult.observe(this) { registrationResult ->
            val (success, message) = registrationResult
            if (success) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                // Handle success, navigate to the next screen if needed
                // For example:
                // startActivity(Intent(this, NextActivity::class.java))
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                // Handle failure, show error message if needed
            }
        }
    }
}