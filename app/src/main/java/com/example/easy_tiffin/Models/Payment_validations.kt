package com.example.easy_tiffin.Models

data class Payment_validations(
    var bank_name: String? = null,
    var last_4digit: String? = null,
    val acc_holder_name: Int? = null,
    val transit_number: Int? = null,
    val account_number: Int? = null,
    val institute_number: Int? = null,
    val Success: String? = null,
    val error: String? = null,
    val isDataValid: Boolean = false

)
