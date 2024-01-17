package com.example.easy_tiffin.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.easy_tiffin.R
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

class Welcome_screen : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 500

    lateinit var Buttn_Get_started: Button
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        analytics = Firebase.analytics
        Buttn_Get_started = findViewById<Button>(R.id.Btn_get_started)


        Buttn_Get_started.setOnClickListener {


            Navigator.navigateTo(Register::class.java, this, finishCurrent = true)


        }


    }

}