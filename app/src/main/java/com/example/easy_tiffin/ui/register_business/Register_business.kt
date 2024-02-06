package com.example.easy_tiffin.ui.register_business

import android.os.Bundle

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.easy_tiffin.R
import com.example.easy_tiffin.Network_Utils.NetworkUtils
import com.example.easy_tiffin.Progress_bar.ProgressBarHandler
import com.example.easy_tiffin.adapter.LocationPredictor
import com.example.easy_tiffin.Models.BusinessDetails
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.example.easy_tiffin.Shared_Preference.SharedPreferencesManager
import com.example.easy_tiffin.Time_stamp.TimestampFormatter
import com.example.easy_tiffin.navigator.Navigator
import com.example.easy_tiffin.ui.login.LoginActivity
import com.example.easy_tiffin.ui.ui.dashboard.Dashboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Register_business : AppCompatActivity() {
   // private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: BusinessViewModel
    private lateinit var placesClient: PlacesClient
    private lateinit var locationPredictor: LocationPredictor
    private lateinit var placeId: String
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var progressBarHandler: ProgressBarHandler
    @Inject
    lateinit var buisiness_Repository: BusinessRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_business)


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


        viewModel = ViewModelProvider(this, BusinessViewModelFactory(buisiness_Repository))
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
//
            val businessName = editTextBusinessName.text.toString().trim()
            val businessAddress = editTextBusinessAddress.text.toString().trim()
//

            // Call ViewModel to add business details
            viewModel.buisiness_validations(businessName,businessAddress)
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
        //viewModel.register_FormState.observe()
        viewModel.register_FormState.observe(this@Register_business, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            progressBarHandler.showLoading(false)

            if (loginState.buisiness_error != null) {
                editTextBusinessName.error = getString(loginState.buisiness_error)
            } else if (loginState.buisiness_address_error != null) {
                editTextBusinessAddress.error = getString(loginState.buisiness_address_error)
            } else {
                if (!NetworkUtils.isNetworkAvailable(this)) {
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                    return@Observer
                }
                progressBarHandler.showLoading(true)
                buttonRegisterBusiness.setText("")
                // showLoading()
                progressBarHandler.showLoading(true)
                val businessName = editTextBusinessName.text.toString().trim()
                val businessAddress = editTextBusinessAddress.text.toString().trim()
                val gst = editTextGST.text.toString().trim()

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
        })
    }

    // Callback function to handle the selected location (placeId)
    fun onLocationSelected(placeId: String) {
        this.placeId = placeId
    }

    private fun navigateToPreviousScreen() {

        Navigator.navigateTo(LoginActivity::class.java, this, finishCurrent = false)

    }
    private fun navigateToNextScreen() {

        Navigator.navigateTo(Dashboard::class.java, this, finishCurrent = true)

    }
}
