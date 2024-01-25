// LoginRepository.kt
package com.example.easy_tiffin.repository

import com.example.easy_tiffin.Models.LoggedInUser
import com.example.easy_tiffin.data.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class LoginRepository(private val firebaseAuth: FirebaseAuth) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    suspend fun logout() {
        firebaseAuth.signOut()
        user = null
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(username, password).await()
            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                val userEmail = firebaseUser.email
                if (userEmail != null) {
                    val loggedInUser = LoggedInUser(firebaseUser.uid, userEmail)
                    setLoggedInUser(loggedInUser)
                    return Result.Success(loggedInUser)
                } else {
                    return Result.Error(Exception("Login failed. Firebase user email is null."))
                }
            } else {
                return Result.Error(Exception("Login failed. Firebase user is null."))
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
