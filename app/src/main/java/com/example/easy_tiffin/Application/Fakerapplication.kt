package com.example.easy_tiffin.Application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Fakerapplication : Application() {



    override fun onCreate() {
        super.onCreate()


        //applicationComponent = Dagger
    }
}