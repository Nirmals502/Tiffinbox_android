package com.example.easy_tiffin.ui.welcome

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.easy_tiffin.R
import com.example.easy_tiffin.Shared_Preference.SharedPreferencesManager
import com.example.easy_tiffin.navigator.Navigator
import com.example.easy_tiffin.ui.food_menu_creator_.Menu_creater
import com.example.easy_tiffin.ui.food_menu_creator_.create_your_menu
import com.example.easy_tiffin.ui.food_menu_creator_.food_menu_creator
import com.example.easy_tiffin.ui.login.LoginActivity
import com.example.easy_tiffin.ui.register_business.Register_business
import com.example.easy_tiffin.ui.stripe_setup.PaymentSetupActivity
import com.example.easy_tiffin.ui.ui.dashboard.Dashboard
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.firestore.FirebaseFirestore

class Welcome_screen : AppCompatActivity() {

    lateinit var Buttn_Get_started: Button
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        analytics = Firebase.analytics
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this)

        Buttn_Get_started = findViewById<Button>(R.id.Btn_get_started)



        Buttn_Get_started.setOnClickListener {


            val userId = SharedPreferencesManager.getInstance(applicationContext).getUserId()


            if (userId != null) {
                val db = FirebaseFirestore.getInstance()
                db.collection("business").document(userId).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null && document.exists()) {
                            // Business details exist, check if they're complete
                            // This could be checking if specific fields are filled
//                            val businessName = document.getString("businessName")
//                            if (businessName != null && businessName.isNotEmpty()) {
                            // Business details are complete, navigate to the main part of the app
                            Navigator.navigateTo(Dashboard::class.java, this, finishCurrent = true)
//                            } else {
//                                // Business details are incomplete, navigate to complete them
//                            }
                        } else {
                            // No business details found, navigate to add them
                            Navigator.navigateTo(
                                Dashboard::class.java,
                                this,
                                finishCurrent = true
                            )
                        }
                    } else {
                        // Handle error (e.g., could not query Firestore)
                    }
                }
            } else {
                // No user ID found, possibly direct to login or registration
                Navigator.navigateTo(
                    LoginActivity::class.java,
                    this,
                    finishCurrent = true
                )
            }


        }


    }

}