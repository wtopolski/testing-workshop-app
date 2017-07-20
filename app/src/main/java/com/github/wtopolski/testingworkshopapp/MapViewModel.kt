package com.github.wtopolski.testingworkshopapp

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.github.wtopolski.testingworkshopapp.binding.ButtonViewBinder
import com.github.wtopolski.testingworkshopapp.binding.RecycleViewBinder
import com.github.wtopolski.testingworkshopapp.binding.TextViewBinder
import com.github.wtopolski.testingworkshopapp.util.ColorUtils
import com.github.wtopolski.testingworkshopapp.util.ColorationType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MapViewModel(val context: Context, val onExecuteBinding: () -> Unit) {
    val compositeDisposable = CompositeDisposable()
    val colorUtil = ColorUtils()
    val colorLabel = TextViewBinder(true, true)
    val clear = ButtonViewBinder(true, true)
    val red = ButtonViewBinder(true, true)
    val green = ButtonViewBinder(true, true)
    val blue = ButtonViewBinder(true, true)
    val adapter = MapViewAdapter(this::onListItemClick)
    val list = RecycleViewBinder<MapViewAdapter>(true, true)

    fun onCreate() {
        setupColorLabel(WHITE)

        clear.value = "Clear"
        red.value = "Red"
        green.value = "Green"
        blue.value = "Blue"

        list.adapter = adapter
        list.configure(true, DefaultItemAnimator(), LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false), null)
    }

    fun onStart() {
        compositeDisposable.add(clear.rxClick(AndroidSchedulers.mainThread()).map { WHITE }.subscribe(this::setupColorLabel))
        compositeDisposable.add(red.rxClick(AndroidSchedulers.mainThread()).map { RED }.subscribe(this::setupColorLabel))
        compositeDisposable.add(green.rxClick(AndroidSchedulers.mainThread()).map { GREEN }.subscribe(this::setupColorLabel))
        compositeDisposable.add(blue.rxClick(AndroidSchedulers.mainThread()).map { BLUE }.subscribe(this::setupColorLabel))

        fillList(colorUtil.colors)
    }

    fun onStop() {
        compositeDisposable.clear()
    }

    fun onDestroy() {
        adapter.data.clear()
    }

    private fun fillList(colors: List<String>) {
        if (adapter.data.isEmpty()) {
            adapter.data.clear()
            adapter.data.addAll(colors)
            adapter.notifyDataSetChanged()
        }
    }

    private fun onListItemClick(newColor: String) {
        val current = colorLabel.value?.toString() ?: WHITE
        setupColorLabel(colorUtil.mix(current, newColor))
        onExecuteBinding()
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