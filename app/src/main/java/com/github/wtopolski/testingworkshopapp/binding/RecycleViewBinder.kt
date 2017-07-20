package com.github.wtopolski.testingworkshopapp.binding

import android.databinding.Bindable
import android.support.v7.widget.RecyclerView

import com.github.wtopolski.testingworkshopapp.BR

class RecycleViewBinder<L : RecyclerView.Adapter<*>>(enabled: Boolean, visibility: Boolean) : BaseBinder(enabled, visibility) {

    @get:Bindable
    var adapter: L? = null
        set(adapter) {
            field = adapter
            notifyPropertyChanged(BR.adapter)
        }

    @get:Bindable
    var fixedSize: Boolean = false
        private set

    @get:Bindable
    var itemAnimator: RecyclerView.ItemAnimator? = null
        private set

    @get:Bindable
    var layoutManager: RecyclerView.LayoutManager? = null
        private set

    @get:Bindable
    var itemDecoration: RecyclerView.ItemDecoration? = null
        private set

    fun configure(fixedSize: Boolean, itemAnimator: RecyclerView.ItemAnimator, layoutManager: RecyclerView.LayoutManager, itemDecoration: RecyclerView.ItemDecoration?) {
        this.fixedSize = fixedSize
        this.itemAnimator = itemAnimator
        this.layoutManager = layoutManager
        this.itemDecoration = itemDecoration
        notifyChange()
    }
}
