/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.adapters.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.roragok.namafia.activities.adapters.recyclerviews.callbacks.DataClassCallback

/**
 * Abstract adapter for recyclerviews that utilize data binding. The view used for each item must declare svariables named: `adapter` and `model`.
 *
 * @param layoutId the android layout resource id to inflate for each item
 * @since 1.0.0
 */
abstract class DataBoundAdapter<T>(private val layoutId: Int) : Adapter<DataBoundViewHolder<T>>() {
    protected val items = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<T> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)

        val viewHolder = DataBoundViewHolder<T>(binding)
        viewHolder.bind(this)

        return viewHolder
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<T>, position: Int) {
        val item = items[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = layoutId

    fun onItemsChanged(newItems: List<T>) {
        val diff = DiffUtil.calculateDiff(DataClassCallback(items, newItems))

        items.clear()
        items += newItems

        diff.dispatchUpdatesTo(this)
    }
}