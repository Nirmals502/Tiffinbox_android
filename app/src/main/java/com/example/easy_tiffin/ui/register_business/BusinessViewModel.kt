package com.example.easy_tiffin.ui.register_business

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_tiffin.R
import com.example.easy_tiffin.Models.BusinessDetails
import com.example.easy_tiffin.Models.buisness_validation
import kotlinx.coroutines.launch
import javax.inject.Inject

class BusinessViewModel @Inject constructor(private val businessRepository: BusinessRepository) : ViewModel() {

    val businessRegistrationResult: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()
    //val Validations: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()
    private val _buissines_valid = MutableLiveData<buisness_validation>()
    val register_FormState: LiveData<buisness_validation> = _buissines_valid


    fun addBusinessDetails(businessDetails: BusinessDetails) {
        viewModelScope.launch {
            try {
                // Validate business details before attempting to register
                if (isValidBusinessDetails(businessDetails)) {
                    businessRepository.addBusinessDetails(businessDetails)
                    businessRegistrationResult.postValue(
                        Pair(
                            true,
                            "Business details added successfully"
                        )
                    )
                } else {
                    businessRegistrationResult.postValue(Pair(false, "Invalid business details"))
                }
            } catch (e: Exception) {
                businessRegistrationResult.postValue(
                    Pair(
                        false,
                        "Failed to add business details: ${e.message}"
                    )
                )
            }
        }
    }

    private fun isValidBusinessDetails(businessDetails: BusinessDetails): Boolean {
        // TODO: Add your validation logic here
        // For example, check if the business name, address, etc., are not empty

        return true // Return true if business details are valid, otherwise false
    }

    fun buisiness_validations(business_name: String, business_address: String) {
        when {
            business_name.isEmpty() -> _buissines_valid.value =
                buisness_validation(buisiness_error = R.string.business_name_error)

            business_address.isEmpty() -> _buissines_valid.value =
                buisness_validation(buisiness_address_error = R.string.business_address_error)

            else -> {
                _buissines_valid.value = buisness_validation(isDataValid = true)
            }
        }
    }
}
