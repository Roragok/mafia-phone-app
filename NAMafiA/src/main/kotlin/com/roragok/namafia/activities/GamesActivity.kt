/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities

import android.os.Bundle
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.roragok.namafia.R
import com.roragok.namafia.activities.adapters.recyclerviews.GamesAdapter
import com.roragok.namafia.activities.adapters.recyclerviews.dividers.BasicItemDecoration
import com.roragok.namafia.activities.viewmodels.GamesViewModel
import com.roragok.namafia.databinding.ActivityGamesBinding
import kotlinx.android.synthetic.main.toolbar_search.view.*
import timber.log.Timber

class GamesActivity : AbstractActivity() {
    private lateinit var viewModel: GamesViewModel
    private lateinit var binding: ActivityGamesBinding
    private val adapter: GamesAdapter by lazy { GamesAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel(GamesViewModel::class)
        binding = ActivityGamesBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar as Toolbar)

        binding.games.adapter = adapter
        binding.games.layoutManager = LinearLayoutManager(this)
        binding.games.addItemDecoration(BasicItemDecoration(this))

        binding.toolbar.search.queryHint = getString(R.string.game_search_hint)
        binding.toolbar.search.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = true

            override fun onQueryTextChange(newText: String): Boolean {
                Timber.v("search changed: $newText")

                viewModel.searchCriteriaChanged(newText)

                return true
            }
        })

        viewModel.gamesObservable.observe(this, Observer {
            Timber.d("games updated")

            adapter.onItemsChanged(it)
        })

        viewModel.onViewCreated()
    }

    override fun onStart() {
        super.onStart()

        binding.toolbar.toolbar_title.text = getString(R.string.games)
    }
}