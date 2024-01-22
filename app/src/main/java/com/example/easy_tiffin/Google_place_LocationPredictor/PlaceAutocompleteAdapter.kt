package com.example.easy_tiffin.adapter
import android.widget.ArrayAdapter
import com.example.easy_tiffin.ui.Register_business
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

class LocationPredictor(private val placesClient: PlacesClient) {

    interface OnLocationSelectedListener {
        fun onLocationSelected(placeId: String)
    }

    fun fetchAddressPredictions(
        query: String,
        adapter: ArrayAdapter<String>,
        listener: Register_business
    ) {
        val bounds = RectangularBounds.newInstance(
            LatLng(41.675537, -141.001219), // Southwest corner of Canada
            LatLng(83.130211, -52.560009) // Northeast corner of Canada
        )

        val request = FindAutocompletePredictionsRequest.builder()
            .setTypeFilter(TypeFilter.ADDRESS)
            .setQuery(query)
            .setLocationBias(bounds)
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            val predictions = response.autocompletePredictions.map { prediction ->
                listener.onLocationSelected(prediction.placeId)
                prediction.getFullText(null).toString()
            }
            adapter.clear()
            adapter.addAll(predictions)
            adapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            // Handle error
            exception.printStackTrace()
        }
    }

    // ... rest of the class
}
