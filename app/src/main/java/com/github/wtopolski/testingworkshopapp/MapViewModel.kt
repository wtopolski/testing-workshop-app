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
    val red = ButtonViewBinder(true, true)
    val green = ButtonViewBinder(true, true)
    val blue = ButtonViewBinder(true, true)

    fun onCreate() {
        setupColorLabel(WHITE)

        clear.value = "Clear"
        red.value = "Red"
        green.value = "Green"
        blue.value = "Blue"
    }

    fun onStart() {
        compositeDisposable.add(clear.rxClick(AndroidSchedulers.mainThread()).map { WHITE }.subscribe(this::setupColorLabel))
        compositeDisposable.add(red.rxClick(AndroidSchedulers.mainThread()).map { RED }.subscribe(this::setupColorLabel))
        compositeDisposable.add(green.rxClick(AndroidSchedulers.mainThread()).map { GREEN }.subscribe(this::setupColorLabel))
        compositeDisposable.add(blue.rxClick(AndroidSchedulers.mainThread()).map { BLUE }.subscribe(this::setupColorLabel))
    }

    fun onStop() {
        compositeDisposable.clear()
    }

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