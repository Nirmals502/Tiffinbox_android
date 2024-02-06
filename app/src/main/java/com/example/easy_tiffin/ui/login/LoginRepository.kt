package com.example.easy_tiffin.ui.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.example.easy_tiffin.Models.LoggedInUser
import com.example.easy_tiffin.Shared_Preference.SharedPreferencesManager
import com.example.easy_tiffin.data.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(private val firebaseAuth: FirebaseAuth, private val sharedPreferencesManager: SharedPreferencesManager) {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(username, password).await()
            val firebaseUser = authResult.user ?: return Result.Error(Exception("No user found."))

            val userEmail = firebaseUser.email ?: return Result.Error(Exception("User email is not available."))
            val loggedInUser = LoggedInUser(firebaseUser.uid, userEmail)
            setLoggedInUser(loggedInUser)  // Ensure this method is correctly implemented
            return Result.Success(loggedInUser)

        } catch (e: FirebaseAuthException) {
            return when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> Result.Error(Exception("Invalid email format."))
                "ERROR_WRONG_PASSWORD" -> Result.Error(Exception("Password does not match."))
                "ERROR_USER_NOT_FOUND" -> Result.Error(Exception("No user found with this email."))
                "ERROR_USER_DISABLED" -> Result.Error(Exception("User account has been disabled."))
                // Add more cases as needed
                else -> Result.Error(Exception("Login failed: ${e.localizedMessage}"))
            }
        } catch (e: Exception) {
            return Result.Error(Exception("An unexpected error occurred: ${e.localizedMessage}"))
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        // Implementation to cache/store the logged-in user's information
        // Store the UID in SharedPreferences
        sharedPreferencesManager.setUserId(loggedInUser.userId)

    }
}
