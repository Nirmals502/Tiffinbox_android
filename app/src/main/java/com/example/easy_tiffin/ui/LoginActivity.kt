package com.example.easy_tiffin.ui

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.easy_tiffin.R
import com.easy_tiffin.databinding.ActivityLoginBinding
import com.example.easy_tiffin.Models.LoggedInUserView
import com.example.easy_tiffin.Progress_bar.ProgressBarHandler
import com.example.easy_tiffin.Shared_Preference.SharedPreferencesManager
import com.example.easy_tiffin.facebook.FacebookHandler

import com.example.easy_tiffin.viewModel.LoginViewModel
import com.example.easy_tiffin.factory.LoginViewModelFactory
import com.example.easy_tiffin.navigator.Navigator
import com.example.easy_tiffin.repository.LoginRepository

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressBarHandler: ProgressBarHandler
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var facebookHandler: FacebookHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        progressBarHandler = ProgressBarHandler(this)
        val LoginRepository = LoginRepository(auth)
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this)
        facebookHandler = FacebookHandler(this)


        val username = binding.editTextTextEmailAddress
        val password = binding.appCompatEditText
        val login = binding.btnSignin
        val facebook = binding.imgFb
        facebook.setOnClickListener {
            facebookHandler.performLogin()

        }
        //val loading = binding.progressBar
        val create_account = binding.txtContainer

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(LoginRepository))
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                // password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            progressBarHandler.showLoading(false)
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            //finish()
        })


        login.setOnClickListener {
            //loading.visibility = View.VISIBLE
            if (username.text.isEmpty()) {
                username.setError("Email is required")
                return@setOnClickListener
            }


            if (!Patterns.EMAIL_ADDRESS.matcher(username.text).matches()) {
                username.setError("Invalid email")
                //Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.text.isNullOrEmpty()) {
                //password.setError("Password is required")
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progressBarHandler.showLoading(true)

            loginViewModel.login(username.text.toString().trim(), password.text.toString().trim())
//                val intent = Intent(this@LoginActivity, setup_account::class.java)
//                startActivity(intent)
//                finish()
        }

        create_account.setOnClickListener {
            navigateToNextScreen()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //facebookHandler.handleLoginResult(requestCode, resultCode, data)
        facebookHandler.handleLoginResult(requestCode, resultCode, data) { email, accessToken ->
            // Handle the obtained email and accessToken as needed
            Log.d("FacebookLogin", "Email in LoginActivity: $email")
            Log.d("FacebookLogin", "Access Token in LoginActivity: $accessToken")
        }

        // facebookHandler.fetchUserDetails()

        // printEmailAndToken()
    }

    private fun navigateToNextScreen() {

        Navigator.navigateTo(Register::class.java, this, finishCurrent = true)

    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        sharedPreferencesManager.setUserId(auth.currentUser?.uid.toString())

        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "Success",
            Toast.LENGTH_LONG
        ).show()
        Navigator.navigateTo(dashboard::class.java, this, finishCurrent = true)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}





