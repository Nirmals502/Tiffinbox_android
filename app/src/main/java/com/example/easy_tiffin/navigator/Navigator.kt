package com.example.easy_tiffin.navigator

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.easy_tiffin.R

object Navigator {

    fun navigateTo(activityClass: Class<out AppCompatActivity>, context: Context, finishCurrent: Boolean = false) {
        val intent = Intent(context, activityClass)

        if (finishCurrent && context is AppCompatActivity) {
            context.startActivity(intent)
            context.overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left)
            context.finish()
        } else {
            context.startActivity(intent)
            if (context is AppCompatActivity) {
                context.overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right)
                context.finish()
            }
        }
    }
}