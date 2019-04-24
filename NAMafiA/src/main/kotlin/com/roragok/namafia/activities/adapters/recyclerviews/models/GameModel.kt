/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.adapters.recyclerviews.models

import com.roragok.namafia.api.entities.Game
import timber.log.Timber

class GameModel(game: Game) : Searchable, Comparable<GameModel> {
    private val signedPlayers: List<String>? = game.signedPlayers
    val id: Int = game.id
    val title: String = game.title ?: ""
    val expired: Boolean = game.expired ?: true

    override fun matchesSearch(searchText: String, ignoreCase: Boolean): Boolean {
        if (title.contains(searchText, ignoreCase)) {
            Timber.v("matched on title: $title")

            return true
        }

        if (id.toString().contains(searchText, ignoreCase)) {
            Timber.v("matched on game id: $id")

            return true
        }

        if (signedPlayers != null && signedPlayers.any { it.contains(searchText, ignoreCase) }) {
            Timber.v("matched on players: $signedPlayers")

            return true
        }

        return false
    }

    override fun compareTo(other: GameModel): Int {
        val compare = expired.compareTo(other.expired)

        if (compare != 0) {
            return compare
        }

        return title.compareTo(other.title, ignoreCase = true)
    }
}