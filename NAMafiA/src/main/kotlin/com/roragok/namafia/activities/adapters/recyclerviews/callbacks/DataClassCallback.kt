/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.adapters.recyclerviews.callbacks

import androidx.recyclerview.widget.DiffUtil

/**
 * Callback for classes that are simple kotlin data classes
 *
 * @since 1.0.0
 */
class DataClassCallback<T>(
    private val originalVehicles: List<T>,
    private val newVehicleList: List<T>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val originalData = originalVehicles[oldItemPosition]
        val newData = newVehicleList[newItemPosition]

        return originalData === newData
    }

    override fun getOldListSize(): Int = originalVehicles.size

    override fun getNewListSize(): Int = newVehicleList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val originalData = originalVehicles[oldItemPosition]
        val newData = newVehicleList[newItemPosition]

        return originalData == newData
    }
}