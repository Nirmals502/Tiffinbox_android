package com.example.TiffinBox.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.TiffinBox.R

class Welcome_screen : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 500

    lateinit var Buttn_Get_started: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        Buttn_Get_started = findViewById<Button>(R.id.Btn_get_started)


        Buttn_Get_started.setOnClickListener {

            val intent = Intent(this@Welcome_screen, LoginActivity::class.java)
            startActivity(intent)
            finish()



        }


    }

}