package com.example.easy_tiffin.firebase_messaging_service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            // Handle the data payload as needed
        }

        // Check if message contains a notification payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            // Handle the notification payload as needed
        }
        if (remoteMessage.data["type"] == "email_verification") {
            // Handle email verification message
            handleEmailVerificationMessage(remoteMessage.data["status"])
        }
    }

    private fun handleEmailVerificationMessage(status: String?) {
        if (status == "verified") {
            // Handle email verification success
            Log.d("FCM", "Email verification successful")
            // Show a toast, update UI, or perform other actions as needed
        } else {
            // Handle email verification failure
            Log.e("FCM", "Email verification failed")
            // Show a toast, update UI, or perform other actions as needed
        }

    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this app's subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        // Implement this method to send the token to your server if needed
        // For example, you can send the token to your backend server for further processing
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}