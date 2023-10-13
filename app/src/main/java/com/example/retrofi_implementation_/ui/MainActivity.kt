package com.example.retrofi_implementation_.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview



class MainActivity : ComponentActivity() {

   // private lateinit var mainViewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




//        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
//        mainViewModel.productsLiveData.observe(this) {
//            val txt = it.joinToString { x -> x.title }
//            Log.d("this", txt)
//        }

        setContent {
           Text(text = "Nirmal")



        }
    }
}



@Preview( showBackground = true)
@Composable
fun GreetingPreview() {
    Text(text = "gff")
}