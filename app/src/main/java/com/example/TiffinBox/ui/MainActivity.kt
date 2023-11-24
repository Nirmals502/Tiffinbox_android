package com.example.TiffinBox.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.TiffinBox.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.TiffinBox.R


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.productsLiveData.observe(this) {
            val txt = it.joinToString { x -> x.title }
            Log.d("this", txt)
        }

        setContent {
            Text(text = "Nirmal")
            GreetingPreview()

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Button(onClick = {/*TODO*/ }) {
        Text(text = "Hello")
        Image(painter = painterResource(id = R.drawable.tiffin_box), contentDescription = "dimmy")
        
    }
}