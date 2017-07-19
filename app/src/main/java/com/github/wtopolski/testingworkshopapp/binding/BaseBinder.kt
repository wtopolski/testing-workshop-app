package com.github.wtopolski.testingworkshopapp.binding

import android.databinding.BaseObservable
import android.databinding.Bindable

import com.github.wtopolski.testingworkshopapp.BR

open class BaseBinder(private var enabled: Boolean = false, private var visibility: Boolean = false) : BaseObservable() {

    @Bindable
    fun getEnabled(): Boolean {
        return enabled
    }

    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
        notifyPropertyChanged(BR.enabled)
    }

    @Bindable
    fun getVisibility(): Boolean {
        return visibility
    }

    fun setVisibility(visibility: Boolean) {
        this.visibility = visibility
        notifyPropertyChanged(BR.visibility)
    }
}
