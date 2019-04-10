/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.roragok.namafia.activities.adapters.recyclerviews.DataBoundAdapter
import com.roragok.namafia.activities.adapters.recyclerviews.GameDetailsAdapter
import com.roragok.namafia.activities.adapters.recyclerviews.dividers.BasicItemDecoration
import com.roragok.namafia.activities.adapters.recyclerviews.models.GameDetailsModel
import com.roragok.namafia.activities.viewmodels.GameDetailsViewModel
import com.roragok.namafia.databinding.ActivityGameDetailsBinding
import com.roragok.namafia.events.InputData
import com.roragok.namafia.events.IntentExtra
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.toolbar_back.view.*

class GameDetailsActivity : AbstractActivity() {
    private lateinit var viewModel: GameDetailsViewModel
    private lateinit var binding: ActivityGameDetailsBinding
    private val adapter: DataBoundAdapter<GameDetailsModel> by lazy { GameDetailsAdapter() }

    private val inputData: GameDetailsInputData by lazy {
        intent.getParcelableExtra<GameDetailsInputData>(IntentExtra.INPUT_DATA) ?: throw IllegalArgumentException("input data was not provided")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel(GameDetailsViewModel::class)
        binding = ActivityGameDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar as Toolbar)

        binding.gameDays.adapter = adapter
        binding.gameDays.layoutManager = LinearLayoutManager(this)
        binding.gameDays.addItemDecoration(BasicItemDecoration(this))
        binding.toolbar.toolbar_title.text = inputData.gameTitle

        viewModel.gameDetailsObservable.observe(this, Observer {
            adapter.onItemsChanged(it)
        })

        viewModel.onViewCreated(inputData.gameId)
    }

    @Parcelize
    data class GameDetailsInputData(val gameId: Int, val gameTitle: String) : InputData
}