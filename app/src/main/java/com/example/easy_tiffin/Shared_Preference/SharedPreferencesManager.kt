package com.example.easy_tiffin.Shared_Preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64

class SharedPreferencesManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val SHARED_PREFERENCES_NAME = "Easy_Tiffin"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_PHONE = "phone"
        private const val KEY_VERIFICATION_ID = "verification_id"

        @Volatile
        private var instance: SharedPreferencesManager? = null

        fun getInstance(context: Context): SharedPreferencesManager {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferencesManager(context).also { instance = it }
            }
        }
    }


    fun saveVerificationId(verificationId: String) {
        val encryptedVerificationId = encryptVerificationId(verificationId)
        sharedPreferences.edit().putString(KEY_VERIFICATION_ID, encryptedVerificationId).apply()
    }

    fun getVerificationId(): String? {
        val encryptedVerificationId = sharedPreferences.getString(KEY_VERIFICATION_ID, null)
        return if (encryptedVerificationId != null) {
            decryptVerificationId(encryptedVerificationId)
        } else {
            null
        }
    }

    fun setUserId(newUserId: String) {
        sharedPreferences.edit().putString(KEY_USER_ID, newUserId).apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun setPhone(newPhone: String) {
        sharedPreferences.edit().putString(KEY_PHONE, newPhone).apply()
    }

    fun getPhone(): String? {
        return sharedPreferences.getString(KEY_PHONE, null)
    }

    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    // Example encryption and decryption methods (replace with your own implementation)
    private fun encryptVerificationId(verificationId: String): String {
        return Base64.encodeToString(verificationId.toByteArray(), Base64.DEFAULT)
    }

    private fun decryptVerificationId(encryptedVerificationId: String): String {
        return String(Base64.decode(encryptedVerificationId, Base64.DEFAULT))
    }
}
