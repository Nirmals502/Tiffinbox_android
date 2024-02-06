package com.example.easy_tiffin.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.easy_tiffin.R
import com.easy_tiffin.databinding.ActivityLoginBinding
import com.example.easy_tiffin.Models.LoggedInUserView
import com.example.easy_tiffin.Network_Utils.NetworkUtils
import com.example.easy_tiffin.Progress_bar.ProgressBarHandler
import com.example.easy_tiffin.facebook.FacebookHandler
import com.example.easy_tiffin.navigator.Navigator
import com.example.easy_tiffin.ui.stripe_setup.StripeService_repository
import com.example.easy_tiffin.ui.register.Register
import com.example.easy_tiffin.ui.ui.dashboard.Dashboard
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressBarHandler: ProgressBarHandler
    private lateinit var facebookHandler: FacebookHandler
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var stripeService: StripeService_repository


    @Inject
    lateinit var loginRepository: LoginRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        analytics.setCurrentScreen(this, "Login Screen", null)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBarHandler = ProgressBarHandler(this)

        facebookHandler = FacebookHandler(this)


        val username = binding.editTextTextEmailAddress
        val password = binding.appCompatEditText
        val login = binding.btnSignin
        val facebook = binding.imgFb
        facebook.setOnClickListener {
            facebookHandler.performLogin()

        }
        val create_account = binding.txtContainer

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(loginRepository))
            .get(LoginViewModel::class.java)
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            progressBarHandler.showLoading(false)

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            } else if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            } else {
                if (!NetworkUtils.isNetworkAvailable(this)) {
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                    return@Observer
                }
                progressBarHandler.showLoading(true)
                loginViewModel.login(
                    username.text.toString().trim(),
                    password.text.toString().trim()
                )
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
            finish()
        })


        login.setOnClickListener {

            loginViewModel.loginDataChanged(
                username.text.toString().trim(),
                password.text.toString().trim()
            )



        }








        create_account.setOnClickListener {
            navigateToNextScreen()
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //facebookHandler.handleLoginResult(requestCode, resultCode, data)
        facebookHandler.handleLoginResult(
            requestCode,
            resultCode,
            data
        ) { email, accessToken, name ->
            // Handle the obtained email and accessToken as needed
            Log.d("FacebookLogin", "Email in LoginActivity: $email")
            Log.d("FacebookLogin", "Access Token in LoginActivity: $accessToken")
            Log.d("FacebookLogin", "Name: $name")
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

        // TODO : initiate successful logged in experience


        Toast.makeText(
            applicationContext,
            "Success",
            Toast.LENGTH_LONG
        ).show()
        Navigator.navigateTo(Dashboard::class.java, this, finishCurrent = true)
    }

    private fun showLoginFailed(errorString: String?) {

        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()

    }

    fun Facebook_Login() {
        facebookHandler.performLogin()
    }
}





