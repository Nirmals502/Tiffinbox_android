package com.example.easy_tiffin.animation

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.CycleInterpolator

class ShakeAnimator(private val view: View) {

    private val shakeAnimation = ObjectAnimator.ofFloat(view, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)

    init {
        shakeAnimation.duration = 800
        shakeAnimation.interpolator = CycleInterpolator(5f)
    }

    fun start() {
        shakeAnimation.start()
    }

    fun cancel() {
        shakeAnimation.cancel()
    }

    fun isRunning(): Boolean {
        return shakeAnimation.isRunning
    }
}
