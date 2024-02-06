package com.example.easy_tiffin.ui.otp


import com.easy_tiffin.R
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easy_tiffin.Shared_Preference.SharedPreferencesManager
import com.example.easy_tiffin.ui.register_business.Register_business
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ozcanalasalvar.otp_view.view.OtpView

class otp_screen : AppCompatActivity() {
    lateinit var resend: TextView
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private lateinit var verificationId: String // Add this field to store the verificationId
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_screen)
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this)
        FirebaseApp.initializeApp(this)

        // Retrieve verificationId from the Intent
        verificationId = sharedPreferencesManager.getVerificationId().toString()

        resend = findViewById(R.id.Resend)
        resend.setOnClickListener {
            // Implement the logic to resend OTP
         //   resendOtp()
        }

        val otpView = findViewById<OtpView>(R.id.otpView)
        otpView.setTextChangeListener(object : OtpView.ChangeListener {
            override fun onTextChange(value: String, completed: Boolean) {
                if (completed) {
                    // OTP entered, verify it
                    verifyOtp(value)
                }
            }
        })
    }

    private fun verifyOtp(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        // Now you can use this credential to sign in the user
        // signInWithPhoneAuthCredential(credential)
        // Use Firebase Auth to sign in with the credential
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Verification successful, handle the success scenario here
                    onVerificationSuccess()
                } else {
                    // Verification failed, handle the failure scenario here
                    onVerificationFailure(task.exception?.message ?: "Verification failed")
                }
            }
    }


    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback is triggered if the auto-retrieval of the verification code is successful.
            // You can use the credential to sign in the user (e.g., auth.signInWithCredential(credential)).
            // signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is triggered if an error occurred during the verification process.
            // Handle the error (e.g., display an error message to the user).
            // You might want to handle this based on your specific use case
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // This callback is triggered when the verification code is successfully sent to the user's phone.
            // Save the verification ID and resending token to use them later if needed.
            // You may also prompt the user to enter the code manually.
            // You can handle this based on your specific use case
        }
    }
    private fun onVerificationSuccess() {
        // Handle the success scenario, e.g., navigate to the next screen
        val intent = Intent(this@otp_screen, Register_business::class.java)
        startActivity(intent)
        finish()
    }

    private fun onVerificationFailure(errorMessage: String) {
        // Handle the failure scenario, e.g., show an error message to the user
        Toast.makeText(this@otp_screen, errorMessage, Toast.LENGTH_SHORT).show()
        // You can also allow the user to retry verification if needed
    }
}



