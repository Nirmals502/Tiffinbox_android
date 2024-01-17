package com.example.easy_tiffin.repository
import com.example.easy_tiffin.data.User
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
            val currentDateTime = System.currentTimeMillis()
            val currentDate = Date(currentDateTime)
            val createdAt =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(currentDate)
            val updatedAt =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(currentDate)

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
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                            currentDate
                        )
                    ).await()

                }
                firebaseAuth.currentUser?.sendEmailVerification()?.await()
                throw Exception("Email already exists. Please verify this email.")

            } else {
                throw Exception("Email already exists and has been verified. Please log in.")
            }


        } catch (e: Exception) {
            // Handle other exceptions
            throw e
        }
    }
}