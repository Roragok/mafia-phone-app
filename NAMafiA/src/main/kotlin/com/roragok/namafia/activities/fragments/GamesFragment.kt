/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.roragok.namafia.activities.adapters.recyclerviews.GamesAdapter
import com.roragok.namafia.activities.adapters.recyclerviews.dividers.BasicItemDecoration
import com.roragok.namafia.activities.viewmodels.GamesViewModel
import com.roragok.namafia.databinding.FragmentGamesBinding
import com.roragok.namafia.events.InputData
import com.roragok.namafia.events.IntentExtra
import kotlinx.android.parcel.Parcelize
import timber.log.Timber

class GamesFragment : AbstractFragment() {
    private lateinit var viewModel: GamesViewModel
    private lateinit var binding: FragmentGamesBinding
    private val adapter: GamesAdapter by lazy { GamesAdapter(viewModel) }
    private val inputData: GamesInputData by lazy { arguments?.getParcelable<GamesInputData>(IntentExtra.INPUT_DATA) ?: throw IllegalArgumentException("${IntentExtra.INPUT_DATA} was not provided") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGamesBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity ?: return

        viewModel = getViewModel(GamesViewModel::class, activityScope = true) ?: return

        binding.games.adapter = adapter
        binding.games.layoutManager = LinearLayoutManager(activity)
        binding.games.addItemDecoration(BasicItemDecoration(activity))

        viewModel.gamesObservable.observe(this, Observer { games ->
            Timber.d("games updated")

            adapter.onItemsChanged(games.filter { it.expired != inputData.active })
        })
    }

    @Parcelize
    data class GamesInputData(val active: Boolean) : InputData
}