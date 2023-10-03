package com.example.retrofi_implementation_

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Fakerapplication : Application() {



    override fun onCreate() {
        super.onCreate()


        //applicationComponent = Dagger
    }
}