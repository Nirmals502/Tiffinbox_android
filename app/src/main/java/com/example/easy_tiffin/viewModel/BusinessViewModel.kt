package com.example.easy_tiffin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easy_tiffin.data.BusinessDetails
import com.example.easy_tiffin.repository.BusinessRepository
import kotlinx.coroutines.launch

class BusinessViewModel(private val businessRepository: BusinessRepository) : ViewModel() {

    val businessRegistrationResult: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()

    fun addBusinessDetails(businessDetails: BusinessDetails) {
        viewModelScope.launch {
            try {
                // Validate business details before attempting to register
                if (isValidBusinessDetails(businessDetails)) {
                    businessRepository.addBusinessDetails(businessDetails)
                    businessRegistrationResult.postValue(Pair(true, "Business details added successfully"))
                } else {
                    businessRegistrationResult.postValue(Pair(false, "Invalid business details"))
                }
            } catch (e: Exception) {
                businessRegistrationResult.postValue(Pair(false, "Failed to add business details: ${e.message}"))
            }
        }
    }

    private fun isValidBusinessDetails(businessDetails: BusinessDetails): Boolean {
        // TODO: Add your validation logic here
        // For example, check if the business name, address, etc., are not empty

        return true // Return true if business details are valid, otherwise false
    }
}
