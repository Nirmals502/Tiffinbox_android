package com.example.easy_tiffin.ui.register

import com.example.easy_tiffin.Models.User
import com.example.easy_tiffin.Time_stamp.TimestampFormatter
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore

) {
    suspend fun registerUser(
        name: String,
        phone: String,
        email: String,
        password: String
    ) {
        var authResult: AuthResult? = null
        try {
            // Attempt to create a new user
            authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: throw Exception("User registration failed")

            // Update user's display name
            val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            authResult.user?.updateProfile(userProfileChangeRequest)?.await()

            // Send email verification
            authResult.user?.sendEmailVerification()?.await()

            // Create user object

            val createdAt =
                TimestampFormatter.getFormattedTimestamp()
            val updatedAt =
                TimestampFormatter.getFormattedTimestamp()

            val user = User(uid, name, phone, email, createdAt, updatedAt)

            // Save user to Firestore
            firestore.collection("users").document(uid).set(user).await()
        } catch (e: FirebaseAuthUserCollisionException) {
            // Check if the email already exists in Firebase Authentication
            // Email exists, but it has not been verified

            firebaseAuth.currentUser?.reload()?.await()
            if (firebaseAuth.currentUser?.isEmailVerified == false) {
                val uid = firebaseAuth.currentUser!!.uid
                if (uid != null) {
                    val userDocRef = firestore.collection("users").document(uid)
                    val currentDateTime = System.currentTimeMillis()
                    val currentDate = Date(currentDateTime)
                    userDocRef.update(
                        "name",
                        name,
                        "phone",
                        phone, // Add other fields you want to update
                        "updatedAt",
                        TimestampFormatter.getFormattedTimestamp()

                    ).await()

                }
                firebaseAuth.currentUser?.sendEmailVerification()?.await()
                throw Exception("Email already exists. Please verify this email.")

            } else {
                throw Exception("Email already exists. Please log in.")
            }


        } catch (e: Exception) {
            // Handle other exceptions
            throw e
        }
    }
}