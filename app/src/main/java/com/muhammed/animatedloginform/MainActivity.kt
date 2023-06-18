package com.muhammed.animatedloginform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muhammed.animatedloginform.databinding.ActivityMainBinding

/**
 * Created by Muhammed Elşami on 18.06.23.
 */

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.nav_bar_color)
        window.navigationBarColor = getColor(R.color.nav_bar_color)
    }
}