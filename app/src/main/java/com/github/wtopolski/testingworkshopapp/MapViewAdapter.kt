package com.github.wtopolski.testingworkshopapp

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.wtopolski.testingworkshopapp.databinding.ViewListItemBinding
import com.github.wtopolski.testingworkshopapp.util.BindingViewHolder

class MapViewAdapter(val onAction: (String) -> Unit) : RecyclerView.Adapter<BindingViewHolder>() {
    val data = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil.inflate<ViewListItemBinding>(LayoutInflater.from(parent.context), R.layout.view_list_item, parent, false)
        binding.viewModel = ListItemViewModel(onAction)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        if (data.size < position) {
            return
        }

        val localBinding = holder.binding
        if (localBinding is ViewListItemBinding) {
            localBinding.viewModel.update(data[position])
            localBinding.executePendingBindings()
        }
    }

    override fun getItemCount() = data.size
}