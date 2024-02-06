package com.example.easy_tiffin.ui.register

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.easy_tiffin.R
import com.example.easy_tiffin.Network_Utils.NetworkUtils
import com.example.easy_tiffin.Progress_bar.ProgressBarHandler
import com.example.easy_tiffin.Shared_Preference.SharedPreferencesManager
import com.example.easy_tiffin.navigator.Navigator
import com.example.easy_tiffin.ui.register_business.Register_business
import com.example.easy_tiffin.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: RegisterViewModel
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    lateinit var phone: String
    lateinit var buttonRegister: Button
    private lateinit var progressBarHandler: ProgressBarHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        val userRepository = UserRepository(auth, FirebaseFirestore.getInstance())
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this)
        progressBarHandler = ProgressBarHandler(this)

        viewModel = ViewModelProvider(this, RegisterViewModelFactory(userRepository))
            .get(RegisterViewModel::class.java)
        buttonRegister = findViewById(R.id.buttonRegister)
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)
        imageViewBack.setOnClickListener {
             // Finish the current activity on back arrow click
            navigateToPreviousScreen()
        }

        buttonRegister.setText("Register")




        viewModel.registrationResult.observe(this) { registrationResult ->
            val (success, message) = registrationResult
            if (success) {
                // Registration successful, navigate to another screen or perform actions
                sharedPreferencesManager.setUserId(auth.currentUser?.uid.toString())
                sharedPreferencesManager.setPhone(phone)

                showSuccessSnackbar()
                progressBarHandler.showLoading(false)

                //hideLoading()

                if (buttonRegister.text != "Continue") {
                    // Only set the button text to "Continue" if it's not already set
                    buttonRegister.setText("Continue")
                    findViewById<TextView>(R.id.Txt_edit).visibility = View.VISIBLE
                }
                findViewById<TextView>(R.id.Txt_edit).visibility = View.VISIBLE
                buttonRegister.isEnabled = true

            } else {
                // Registration failed, show error message
                findViewById<EditText>(R.id.editTextFullName).isEnabled = true
                findViewById<EditText>(R.id.editTextPhone).isEnabled = true
                findViewById<EditText>(R.id.editTextEmail).isEnabled = true
                findViewById<EditText>(R.id.editTextPassword).isEnabled = true
                findViewById<EditText>(R.id.editTextConfirmPassword).isEnabled = false
                val enabled_color = ContextCompat.getColor(this, R.color.white)
                findViewById<EditText>(R.id.editTextFullName).setTextColor(enabled_color)
                findViewById<EditText>(R.id.editTextPhone).setTextColor(enabled_color)
                findViewById<EditText>(R.id.editTextEmail).setTextColor(enabled_color)
                findViewById<EditText>(R.id.editTextPassword).setTextColor(enabled_color)
                findViewById<EditText>(R.id.editTextConfirmPassword).setTextColor(enabled_color)
                findViewById<TextView>(R.id.Txt_edit).visibility = View.GONE


                // Enable the button
                buttonRegister.isEnabled = true

                buttonRegister.setText("Register")
                progressBarHandler.showLoading(false)
                //hideLoading()

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

            // Hide progress bar regardless of success or failure

        }

        val textview = findViewById<TextView>(R.id.Txt_edit)
        textview.setOnClickListener {
            val enabled_color = ContextCompat.getColor(this, R.color.white)
            findViewById<EditText>(R.id.editTextFullName).isEnabled = true
            findViewById<EditText>(R.id.editTextPhone).isEnabled = true
            findViewById<EditText>(R.id.editTextEmail).isEnabled = true
            findViewById<EditText>(R.id.editTextPassword).isEnabled = true
            findViewById<EditText>(R.id.editTextConfirmPassword).isEnabled = true
            findViewById<EditText>(R.id.editTextFullName).setTextColor(enabled_color)
            findViewById<EditText>(R.id.editTextPhone).setTextColor(enabled_color)
            findViewById<EditText>(R.id.editTextEmail).setTextColor(enabled_color)
            findViewById<EditText>(R.id.editTextPassword).setTextColor(enabled_color)
            findViewById<EditText>(R.id.editTextConfirmPassword).setTextColor(enabled_color)

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
                    progressBarHandler.showLoading(true)
                    //showLoading()
                    buttonRegister.setText("")

                    navigateToNextScreen()
                } else {
                    // User is signed out or email is not verified
                    // You can handle this case as needed
                    //showLoading()
                    progressBarHandler.showLoading(true)
                    buttonRegister.setText("")
                    showSuccessSnackbar()
                }
            } else {
                val fullName = findViewById<EditText>(R.id.editTextFullName).text.toString().trim()
                phone = findViewById<EditText>(R.id.editTextPhone).text.toString().trim()
                val email = findViewById<EditText>(R.id.editTextEmail).text.toString().trim()
                val password = findViewById<EditText>(R.id.editTextPassword).text.toString().trim()
                val confirmPassword =
                    findViewById<EditText>(R.id.editTextConfirmPassword).text.toString().trim()


                if (fullName.isEmpty()) {
                    findViewById<EditText>(R.id.editTextFullName).setError("Full Name is required")
                    return@setOnClickListener
                }

                if (phone.isEmpty()) {
                    findViewById<EditText>(R.id.editTextPhone).setError("Phone number is required")
                    return@setOnClickListener
                }

                if (email.isEmpty()) {
                    findViewById<EditText>(R.id.editTextEmail).setError("Email is required")
                    //Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    // findViewById<EditText>(R.id.editTextPassword).
                    Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (confirmPassword.isEmpty()) {
                    //findViewById<EditText>(R.id.editTextConfirmPassword).setError("Confirm Password is required")
                    Toast.makeText(this, "Confirm Password is required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    findViewById<EditText>(R.id.editTextEmail).setError("Invalid email")
                    //Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (password.length < 6) {
                    //findViewById<EditText>(R.id.editTextPassword).setError("Password should be at least 6 characters")
                    Toast.makeText(
                        this,
                        "Password should be at least 6 characters",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return@setOnClickListener
                }
                if (password != confirmPassword) {
                    //findViewById<EditText>(R.id.editTextPassword).setError("Password and Confirm Password do not match")
                    Toast.makeText(
                        this,
                        "Password and Confirm Password do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                    // findViewById<EditText>(R.id.editTextConfirmPassword).setError("Password and Confirm Password do not match")
                    return@setOnClickListener
                }

                // Show the progress bar while registering
                val disableTextColor = ContextCompat.getColor(this, R.color.disable_text)
                viewModel.registerUser(fullName, phone, email, password)
                findViewById<EditText>(R.id.editTextFullName).isEnabled = false
                findViewById<EditText>(R.id.editTextPhone).isEnabled = false
                findViewById<EditText>(R.id.editTextEmail).isEnabled = false
                findViewById<EditText>(R.id.editTextPassword).isEnabled = false
                findViewById<EditText>(R.id.editTextConfirmPassword).isEnabled = false
                findViewById<EditText>(R.id.editTextFullName).setTextColor(disableTextColor)
                findViewById<EditText>(R.id.editTextPhone).setTextColor(disableTextColor)
                findViewById<EditText>(R.id.editTextEmail).setTextColor(disableTextColor)
                findViewById<EditText>(R.id.editTextPassword).setTextColor(disableTextColor)
                findViewById<EditText>(R.id.editTextConfirmPassword).setTextColor(disableTextColor)
                buttonRegister.isEnabled = false
                //showLoading()
                progressBarHandler.showLoading(true)
                buttonRegister.setText("")

            }

        }

    }

    private fun showSuccessSnackbar() {
        //hideLoading()
        progressBarHandler.showLoading(false)
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

    private fun navigateToPreviousScreen() {

        Navigator.navigateTo(LoginActivity::class.java, this, finishCurrent = false)

    }
}
