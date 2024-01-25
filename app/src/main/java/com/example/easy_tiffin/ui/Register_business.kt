package com.example.easy_tiffin.ui

import android.os.Bundle

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.easy_tiffin.R
import com.example.easy_tiffin.Progress_bar.ProgressBarHandler
import com.example.easy_tiffin.adapter.LocationPredictor
import com.example.easy_tiffin.data.BusinessDetails
import com.example.easy_tiffin.factory.BusinessViewModelFactory
import com.example.easy_tiffin.repository.BusinessRepository
import com.example.easy_tiffin.viewModel.BusinessViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.easy_tiffin.Shared_Preference.SharedPreferencesManager
import com.example.easy_tiffin.Time_stamp.TimestampFormatter
import com.example.easy_tiffin.navigator.Navigator

class Register_business : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: BusinessViewModel
    private lateinit var placesClient: PlacesClient
    private lateinit var locationPredictor: LocationPredictor
    private lateinit var placeId: String
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var progressBarHandler: ProgressBarHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_business)

        // Initialize Firebase, Google Places, and SharedPreferencesManager
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        Places.initialize(applicationContext, getString(R.string.google_maps_api_key))
        placesClient = Places.createClient(this)
        locationPredictor = LocationPredictor(placesClient)
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this)
        progressBarHandler = ProgressBarHandler(this)
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)

        imageViewBack.setOnClickListener {
            // Finish the current activity on back arrow click
            navigateToPreviousScreen()
        }


        // Initialize ViewModel and Repository
        val businessRepository = BusinessRepository(auth, FirebaseFirestore.getInstance())
        viewModel = ViewModelProvider(this, BusinessViewModelFactory(businessRepository))
            .get(BusinessViewModel::class.java)

        // UI components
        val buttonRegisterBusiness: Button = findViewById(R.id.button_Register_buisiness)
        val editTextBusinessName: EditText = findViewById(R.id.editTextBusinessName)
        val editTextBusinessAddress: AutoCompleteTextView =
            findViewById(R.id.autoCompleteTextViewStreet)
        val editTextGST: EditText = findViewById(R.id.editTextGST)
        val aptUnit: EditText = findViewById(R.id.apt_unit)

        // Retrieve user id and phone from SharedPreferences
        val userId = sharedPreferencesManager.getUserId()
        val phone = sharedPreferencesManager.getPhone()

        // Register business button click listener
        buttonRegisterBusiness.setOnClickListener {
            val businessName = editTextBusinessName.text.toString().trim()
            val businessAddress = editTextBusinessAddress.text.toString().trim()
            val gst = editTextGST.text.toString().trim()

            // Validate business name and address
            if (businessName.isEmpty()) {
                editTextBusinessName.error = "Business Name is required"
                return@setOnClickListener
            }

            if (businessAddress.isEmpty()) {
                editTextBusinessAddress.error = "Business Address is required"
                return@setOnClickListener
            }
            buttonRegisterBusiness.setText("")
            // showLoading()
            progressBarHandler.showLoading(true)
            // You can add additional validations as needed

            // Create BusinessDetails object
            val businessDetails = BusinessDetails(
                businessName = businessName,
                Address = businessAddress,
                gst = gst,
                Apt_Unit_Number = aptUnit.text.toString().trim(),
                Userid = userId.toString(),
                createdAt = TimestampFormatter.getFormattedTimestamp(),
                updatedAt = TimestampFormatter.getFormattedTimestamp(),
                status = "Active",
                Phone = phone.toString(),
                Place_ID = placeId
            )

            // Call ViewModel to add business details
            viewModel.addBusinessDetails(businessDetails)
        }

        // AutoCompleteTextView setup
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)
        editTextBusinessAddress.setAdapter(adapter)

        // Item click and text change listeners for AutoCompleteTextView
        editTextBusinessAddress.setOnItemClickListener { _, _, position, _ ->
            val selectedPrediction = adapter.getItem(position)
            editTextBusinessAddress.setText(selectedPrediction)
        }

        editTextBusinessAddress.addTextChangedListener { editable ->
            locationPredictor.fetchAddressPredictions(editable.toString(), adapter, this)
        }

        // Observer for business registration result
        viewModel.businessRegistrationResult.observe(this) { registrationResult ->
            val (success, message) = registrationResult
            if (success) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                buttonRegisterBusiness.setText("Register Business Detail")
                progressBarHandler.showLoading(false)
                navigateToNextScreen()
//                hideLoading()
                // Handle success, navigate to the next screen if needed
                // For example:
                // startActivity(Intent(this, NextActivity::class.java))
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                buttonRegisterBusiness.setText("Register Business Detail")
                progressBarHandler.showLoading(false)

                // Handle failure, show error message if needed
            }
        }
    }

    // Callback function to handle the selected location (placeId)
    fun onLocationSelected(placeId: String) {
        this.placeId = placeId
    }

    private fun navigateToPreviousScreen() {

        Navigator.navigateTo(LoginActivity::class.java, this, finishCurrent = false)

    }
    private fun navigateToNextScreen() {

        Navigator.navigateTo(dashboard::class.java, this, finishCurrent = true)

    }
}
