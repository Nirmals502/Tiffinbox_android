package com.example.easy_tiffin.Application

import android.app.Application
import com.easy_tiffin.R
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp
import com.stripe.android.Stripe
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {



    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        Places.initialize(applicationContext, getString(R.string.google_maps_api_key))
      //  val stripe = Stripe(applicationContext, "pk_test_51Oa9LWG17HEZmTmstjvgjeACll8WsoOfkqNEPQyNvD6MNmi1teZjL78hNidlYpKL2zMtr1bUQBL9WlU8leEddFST00HtI4B8Eh")

        //applicationComponent = Dagger
    }
}