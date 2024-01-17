package com.example.easy_tiffin.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.easy_tiffin.R
import com.easy_tiffin.databinding.ActivityDashboardBinding

class dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
     //   setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel("https://bit.ly/2YoJ77H", ""))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", ""))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", ""))

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        //imageSlider.setImageList(imageList,scal)
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
    }
}