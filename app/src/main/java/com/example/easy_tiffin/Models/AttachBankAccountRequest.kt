package com.example.easy_tiffin.Models

data class AttachBankAccountRequest(
    val bankAccountToken: String,
    val stripeAccountId: String
    // Add other fields that your cloud function expects
)
