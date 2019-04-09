/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.adapters.recyclerviews

import com.roragok.namafia.R
import com.roragok.namafia.activities.adapters.recyclerviews.models.GameModel
import com.roragok.namafia.activities.viewmodels.GamesViewModel
import timber.log.Timber

class GamesAdapter(
    private val viewModel: GamesViewModel
) : DataBoundAdapter<GameModel>(R.layout.listitem_game) {

    override fun onBindViewHolder(holder: DataBoundViewHolder<GameModel>, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnClickListener {
            val game = items[position]

            Timber.d("game clicked: ${game.title}")

            viewModel.onGameClicked(game)
        }
    }
}