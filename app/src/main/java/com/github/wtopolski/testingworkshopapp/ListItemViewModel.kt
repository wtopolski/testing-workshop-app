package com.github.wtopolski.testingworkshopapp

import android.graphics.Color
import com.github.wtopolski.testingworkshopapp.binding.TextViewBinder
import com.github.wtopolski.testingworkshopapp.util.ColorUtils
import com.github.wtopolski.testingworkshopapp.util.ColorationType

class ListItemViewModel(val onAction: (String) -> Unit) {
    val colorUtil = ColorUtils()
    val color = TextViewBinder(true, true)
    var value = MapViewModel.WHITE

    fun update(value: String) {
        this.value = value

        val colorInt = Color.parseColor(value)
        val red = Color.red(colorInt)
        val blue = Color.blue(colorInt)
        val green = Color.green(colorInt)
        val inverseColor = colorUtil.generateColor(red, green, blue, ColorationType.INVERSE)

        color.value  = value
        color.textColor = Color.parseColor(inverseColor)
        color.backgroundColor = Color.parseColor(value)
    }

    fun onClick() = onAction(value)
}
