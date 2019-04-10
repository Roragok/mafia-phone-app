/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.api.entities

import com.squareup.moshi.Json

data class GameDetail(
    @Json(name = "day_start") val dayStart: Long,
    @Json(name = "day_id") val id: Long,
    @Json(name = "host") val host: String,
    @Json(name = "day_url") val url: String,
    @Json(name = "status") val status: Boolean,
    @Json(name = "day") val day: Int,
    @Json(name = "day_title") val title: String,
    @Json(name = "parent_id") val parentId: String,
    @Json(name = "alive_players") val alivePlayers: List<String>?,
    @Json(name = "votes") val votes: List<PlayerVote>?
)