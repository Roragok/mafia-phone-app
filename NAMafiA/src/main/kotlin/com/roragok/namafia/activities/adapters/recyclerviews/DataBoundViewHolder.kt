/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.adapters.recyclerviews

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.roragok.namafia.BR

class DataBoundViewHolder<T>(private val binding: ViewDataBinding) : ViewHolder(binding.root) {
    fun bind(item: T) {
        binding.setVariable(BR.model, item)
    }

    fun bind(adapter: DataBoundAdapter<T>) {
//        binding.setVariable(BR.adapter, adapter)
//        binding.executePendingBindings()
    }
}