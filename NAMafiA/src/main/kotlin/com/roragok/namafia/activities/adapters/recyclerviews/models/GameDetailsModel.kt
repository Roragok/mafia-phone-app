/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.adapters.recyclerviews.models

import com.roragok.namafia.api.entities.GameDetail

data class GameDetailsModel(private val gameDetail: GameDetail) : Comparable<GameDetailsModel> {
    val title = gameDetail.title
    val day = gameDetail.day
    val host = gameDetail.host
    val playersAlive = gameDetail.alivePlayers
    val playersAliveAsString: String
        get() = gameDetail.alivePlayers?.joinToString(", ") ?: ""

    override fun compareTo(other: GameDetailsModel): Int =
        day.compareTo(other.day) * -1 // -1 to invert
}