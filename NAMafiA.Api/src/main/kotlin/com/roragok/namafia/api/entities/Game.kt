/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.api.entities

import com.squareup.moshi.Json

data class Game(
    @Json(name = "game_id") val id: Int,
    @Json(name = "signed_players") val signedPlayers: List<String>? = null,
    @Json(name = "game_url") val url: String? = null,
    @Json(name = "host") val host: String? = null,
    @Json(name = "status") val expired: Boolean? = null,
    @Json(name = "game_start") val start: Long? = null,
    @Json(name = "title") val title: String? = null
)