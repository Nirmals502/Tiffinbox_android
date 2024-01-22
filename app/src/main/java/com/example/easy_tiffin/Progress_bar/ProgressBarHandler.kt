package com.example.easy_tiffin.Progress_bar

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import com.easy_tiffin.R

class ProgressBarHandler(private val activity: Activity) {

    private val progressBar: ProgressBar = activity.findViewById(R.id.progressBar)

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            disableScreen()
        } else {
            progressBar.visibility = View.GONE
            enableScreen()
        }
    }

    private fun disableScreen() {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun enableScreen() {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}
