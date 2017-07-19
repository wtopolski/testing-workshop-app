package com.github.wtopolski.testingworkshopapp

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.wtopolski.testingworkshopapp.binding.TextViewBinder
import com.github.wtopolski.testingworkshopapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val colorLabel = TextViewBinder(true, true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.colorLabelHandler = colorLabel
    }

    override fun onStart() {
        super.onStart()
        colorLabel.value = "#FFFFFF"
        colorLabel.textColor = Color.parseColor("#FFFFFF")
        colorLabel.backgroundColor = Color.parseColor("#FF0000")
    }
}
