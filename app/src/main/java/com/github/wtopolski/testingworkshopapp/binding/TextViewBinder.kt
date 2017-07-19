package com.github.wtopolski.testingworkshopapp.binding

import android.databinding.Bindable
import com.github.wtopolski.testingworkshopapp.BR

open class TextViewBinder(enabled: Boolean, visibility: Boolean) : BaseBinder(enabled, visibility) {

    @get:Bindable
    var value: CharSequence? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.value)
        }

    @get:Bindable
    var textColor: Int? = null
        set(textColor) {
            field = textColor
            notifyPropertyChanged(BR.textColor)
        }

    @get:Bindable
    var backgroundColor: Int? = null
        set(backgroundColor) {
            field = backgroundColor
            notifyPropertyChanged(BR.backgroundColor)
        }
}
