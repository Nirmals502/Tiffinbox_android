package com.example.easy_tiffin.repository

import android.util.Log
import com.example.easy_tiffin.data.BusinessDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BusinessRepository(private val auth: FirebaseAuth, private val firestore: FirebaseFirestore) {
    val db = FirebaseFirestore.getInstance()
    fun addBusinessDetails(businessDetails: BusinessDetails) {
        val businessCollection = db.collection("business")

        // Add a document without specifying a document ID
        val newBusinessDocument = businessCollection.document()

        // Set data for the document
        newBusinessDocument.set(businessDetails)
            .addOnSuccessListener {
                // Document added successfully
                Log.d("BusinessRepository", "Business details added successfully for user")
            }
            .addOnFailureListener { e ->
                // Handle failure
                Log.e("BusinessRepository", "Error adding business details for user: ", e)
            }
    }
}
