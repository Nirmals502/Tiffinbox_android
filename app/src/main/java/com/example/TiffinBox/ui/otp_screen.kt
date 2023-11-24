package com.example.TiffinBox.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.TiffinBox.R

class otp_screen : AppCompatActivity() {
    lateinit var resend: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_screen)
        resend = findViewById(R.id.Resend)
        resend.setOnClickListener {
            navigation()
        }
    }

    fun navigation() {
        val intent = Intent(this@otp_screen, dashboard::class.java)
        startActivity(intent)
        finish()
    }
}