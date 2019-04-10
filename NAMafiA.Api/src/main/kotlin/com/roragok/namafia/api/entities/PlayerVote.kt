/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.api.entities

import com.squareup.moshi.Json

data class PlayerVote(
    @Json(name = "vote_time") val voteTime: Long,
    @Json(name = "vote") val vote: String?,
    @Json(name = "voter") val voter: String?
)