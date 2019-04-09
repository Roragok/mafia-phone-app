/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.api.retrofit

import com.roragok.namafia.api.entities.Game
import io.reactivex.Single
import retrofit2.http.GET

interface NamafiaService {
    /**
     * Attempts to fetch all available games
     *
     * @since 1.0.0
     */
    @GET("getGames")
    fun fetchAllGames(): Single<List<Game>>
}