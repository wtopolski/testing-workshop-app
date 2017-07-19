package com.github.wtopolski.testingworkshopapp

import android.graphics.Color
import com.github.wtopolski.testingworkshopapp.binding.ButtonViewBinder
import com.github.wtopolski.testingworkshopapp.binding.TextViewBinder
import com.github.wtopolski.testingworkshopapp.util.ColorUtils
import com.github.wtopolski.testingworkshopapp.util.ColorationType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MapViewModel {
    val compositeDisposable = CompositeDisposable()
    val colorUtil = ColorUtils()
    val colorLabel = TextViewBinder(true, true)
    val clear = ButtonViewBinder(true, true)

    fun onCreate() {
        setupColorLabel(WHITE)

        clear.value = "Clear"
    }

    fun onStart() {
        compositeDisposable.add(clear.rxClick(AndroidSchedulers.mainThread()).subscribe(this::clearColorLabel))
    }

    fun onStop() {
        compositeDisposable.clear()
    }

    private fun clearColorLabel(timestamp: Long) = setupColorLabel(WHITE)

    private fun setupColorLabel(color: String) {
        val colorInt = Color.parseColor(color)
        val red = Color.red(colorInt)
        val blue = Color.blue(colorInt)
        val green = Color.green(colorInt)
        val inverseColor = colorUtil.generateColor(red, green, blue, ColorationType.INVERSE)

        colorLabel.value  = color
        colorLabel.textColor = Color.parseColor(inverseColor)
        colorLabel.backgroundColor = Color.parseColor(color)
    }

    companion object {
        const val WHITE = "#FFFFFF"
        const val RED = "#FF0000"
        const val GREEN = "#00FF00"
        const val BLUE = "#0000FF"
    }
}