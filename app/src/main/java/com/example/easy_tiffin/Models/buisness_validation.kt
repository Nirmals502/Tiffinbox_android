package com.example.easy_tiffin.Models

data class buisness_validation(
    val buisiness_error: Int? = null,
    val buisiness_address_error: Int? = null,
    val isDataValid: Boolean = false
)
