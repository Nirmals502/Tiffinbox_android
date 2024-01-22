package com.example.easy_tiffin.Application

import android.app.Application
import com.easy_tiffin.R
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Fakerapplication : Application() {



    override fun onCreate() {
        super.onCreate()

        Places.initialize(applicationContext, getString(R.string.google_maps_api_key))
        //applicationComponent = Dagger
    }
}