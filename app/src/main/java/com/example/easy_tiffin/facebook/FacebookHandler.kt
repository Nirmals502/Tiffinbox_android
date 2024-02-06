package com.example.easy_tiffin.facebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.appevents.AppEventsLogger

class FacebookHandler(private val activity: Activity) {

    private val callbackManager = CallbackManager.Factory.create()

    // Listener to handle Facebook login result
    private val facebookCallback = object : FacebookCallback<com.facebook.login.LoginResult> {
        override fun onSuccess(loginResult: com.facebook.login.LoginResult) {

        }

        override fun onCancel() {
            // Handle login cancellation
        }

        override fun onError(error: FacebookException) {
            // Handle login error
            Toast.makeText(
                activity,
                error.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    init {
        FacebookSdk.sdkInitialize(activity.applicationContext)
        AppEventsLogger.activateApp(activity.application)
        // Register callback with the LoginManager
        com.facebook.login.LoginManager.getInstance()
            .registerCallback(callbackManager, facebookCallback)
    }

    // Start Facebook login
    fun performLogin() {
        com.facebook.login.LoginManager.getInstance()
            .logInWithReadPermissions(activity, listOf("email", "public_profile"))
    }

    // Handle Facebook login result (call this in onActivityResult of your Activity)
    fun handleLoginResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        callback: (email: String?, accessToken: String?, name: String?) -> Unit
    ) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        val accessToken = AccessToken.getCurrentAccessToken()

        if (accessToken != null && !accessToken.isExpired) {
            // Access token is valid
            fetchEmailFromGraphAPI(accessToken) { email, name ->
                callback.invoke(email, accessToken.token, name)
            }
        } else {
            callback.invoke(null, null, null)
        }
    }

    // Check if the user is currently logged in
    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }

    // Perform Facebook logout
    fun performLogout() {
        com.facebook.login.LoginManager.getInstance().logOut()
    }

    // Inside your FacebookHandler class
    private fun fetchEmailFromGraphAPI(
        accessToken: AccessToken,
        callback: (String?, String?) -> Unit
    ) {
        // Use Graph API to fetch user details including email
        val request = GraphRequest.newMeRequest(accessToken) { json, response ->
            if (json != null) {
                // Parse the JSON object to get user details
                val email = json.optString("email", "")
                val name = json.optString("name", "")

                // Call the callback function with the email and name
                callback.invoke(email, name)
            }
        }

        // Set parameters for the Graph API request
        val parameters = Bundle()
        parameters.putString("fields", "email,name")
        request.parameters = parameters

        // Execute the Graph API request asynchronously
        request.executeAsync()
    }
}
