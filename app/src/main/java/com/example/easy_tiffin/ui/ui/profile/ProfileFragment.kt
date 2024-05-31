package com.example.easy_tiffin.ui.ui.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.easy_tiffin.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize settings options
        val accountSettings = view.findViewById<TextView>(R.id.settings_account)
        val notificationSettings = view.findViewById<TextView>(R.id.settings_notifications)
        val privacySettings = view.findViewById<TextView>(R.id.settings_privacy)
        val logout = view.findViewById<TextView>(R.id.settings_logout)

        accountSettings.setOnClickListener {
            // Navigate to Account Settings Fragment/Activity
            Toast.makeText(context, "Account Settings Clicked", Toast.LENGTH_SHORT).show()
        }

        notificationSettings.setOnClickListener {
            // Navigate to Notification Settings Fragment/Activity
            Toast.makeText(context, "Notification Settings Clicked", Toast.LENGTH_SHORT).show()
        }

        privacySettings.setOnClickListener {
            // Navigate to Privacy Settings Fragment/Activity
            Toast.makeText(context, "Privacy Settings Clicked", Toast.LENGTH_SHORT).show()
        }

        logout.setOnClickListener {
            // Perform logout action
            Toast.makeText(context, "Logout Clicked", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
