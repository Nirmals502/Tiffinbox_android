package com.example.easy_tiffin.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.easy_tiffin.databinding.ContentSetupAccountBinding


class setup_account : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ContentSetupAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentSetupAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val Continue = binding.BtnCtn
        Continue.setOnClickListener {

            val intent = Intent(this@setup_account, otp_screen::class.java)
            startActivity(intent)
            finish()
        }





    }


}