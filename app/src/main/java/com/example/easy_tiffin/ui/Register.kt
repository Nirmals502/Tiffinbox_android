package com.example.easy_tiffin.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.easy_tiffin.R
import com.example.easy_tiffin.Network_Utils.NetworkUtils
import com.example.easy_tiffin.Shared_Preference.SharedPreferencesManager
import com.example.easy_tiffin.factory.RegisterViewModelFactory
import com.example.easy_tiffin.repository.UserRepository
import com.example.easy_tiffin.viewModel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: RegisterViewModel
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    lateinit var phone: String
    lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        val userRepository = UserRepository(auth, FirebaseFirestore.getInstance())
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this)

        viewModel = ViewModelProvider(this, RegisterViewModelFactory(userRepository))
            .get(RegisterViewModel::class.java)
        buttonRegister = findViewById(R.id.buttonRegister)
        buttonRegister.setText("Register")




        viewModel.registrationResult.observe(this) { registrationResult ->
            val (success, message) = registrationResult
            if (success) {
                // Registration successful, navigate to another screen or perform actions
                sharedPreferencesManager.userId = auth.currentUser?.uid
                sharedPreferencesManager.phone = phone

                showSuccessSnackbar()

                hideLoading()

                if (buttonRegister.text != "Continue") {
                    // Only set the button text to "Continue" if it's not already set
                    buttonRegister.setText("Continue")
                    findViewById<TextView>(R.id.Edit).visibility = View.VISIBLE
                }
                findViewById<TextView>(R.id.Edit).visibility = View.VISIBLE
                buttonRegister.isEnabled = true

            } else {
                // Registration failed, show error message
                findViewById<EditText>(R.id.editTextFullName).isEnabled = true
                findViewById<EditText>(R.id.editTextPhone).isEnabled = true
                findViewById<EditText>(R.id.editTextEmail).isEnabled = true
                findViewById<EditText>(R.id.editTextPassword).isEnabled = true

                // Enable the button
                buttonRegister.isEnabled = true

                buttonRegister.setText("Register")
                hideLoading()

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

            // Hide progress bar regardless of success or failure

        }

        val textview =findViewById<TextView>(R.id.Edit)
        textview.setOnClickListener {
            findViewById<EditText>(R.id.editTextFullName).isEnabled = true
            findViewById<EditText>(R.id.editTextPhone).isEnabled = true
            findViewById<EditText>(R.id.editTextEmail).isEnabled = true
            findViewById<EditText>(R.id.editTextPassword).isEnabled = true

            // Enable the button
            buttonRegister.isEnabled = true

            buttonRegister.setText("Register")
        }
        buttonRegister.setOnClickListener {
            if (!NetworkUtils.isNetworkAvailable(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (buttonRegister.text == "Continue") {
                // Check email verification status

                val user: FirebaseUser? = auth.currentUser


                user!!.reload()
                // Add a delay of 2000 milliseconds (2 seconds), adjust as needed
                Handler(Looper.getMainLooper()).postDelayed({
                    user!!.reload()
                }, 2000)

                if (user != null && user.isEmailVerified) {
                    // User is signed in and email is verified
                    buttonRegister.isEnabled = false
                    showLoading()
                    buttonRegister.setText("")

                    navigateToNextScreen()
                } else {
                    // User is signed out or email is not verified
                    // You can handle this case as needed
                    showLoading()
                    buttonRegister.setText("")
                    showSuccessSnackbar()
                }
            } else {
                val fullName = findViewById<EditText>(R.id.editTextFullName).text.toString().trim()
                phone = findViewById<EditText>(R.id.editTextPhone).text.toString().trim()
                val email = findViewById<EditText>(R.id.editTextEmail).text.toString().trim()
                val password = findViewById<EditText>(R.id.editTextPassword).text.toString().trim()
                val confirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword).text.toString().trim()


                if (fullName.isEmpty()) {
                    Toast.makeText(this, "Full Name is required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (phone.isEmpty()) {
                    Toast.makeText(this, "Phone number is required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (email.isEmpty()) {
                    Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (confirmPassword.isEmpty()) {
                    Toast.makeText(this, "Confirm Password is required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (password.length < 6) {
                    Toast.makeText(
                        this,
                        "Password should be at least 6 characters",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return@setOnClickListener
                }
                if (password != confirmPassword) {
                    Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val currentDateTime = System.currentTimeMillis()
                val currentDate = Date(currentDateTime)
                // Show the progress bar while registering

                viewModel.registerUser(fullName, phone, email, password)
                findViewById<EditText>(R.id.editTextFullName).isEnabled = false
                findViewById<EditText>(R.id.editTextPhone).isEnabled = false
                findViewById<EditText>(R.id.editTextEmail).isEnabled = false
                findViewById<EditText>(R.id.editTextPassword).isEnabled = false
                buttonRegister.isEnabled = false
                showLoading()
                buttonRegister.setText("")

            }

        }

    }

    private fun showSuccessSnackbar() {
        hideLoading()
        buttonRegister.setText("Continue")
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "Registration successful. Please verify your email to continue.",
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }

    private fun navigateToNextScreen() {

        Navigator.navigateTo(Register_business::class.java, this, finishCurrent = true)

    }

    private fun showLoading() {
        // Show loading indicator
        findViewById<ProgressBar>(R.id.progressBar2).visibility = View.VISIBLE
    }

    private fun hideLoading() {
        // Hide loading indicator
        findViewById<ProgressBar>(R.id.progressBar2).visibility = View.GONE
    }
}
