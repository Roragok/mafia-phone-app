/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.api.retrofit

import com.roragok.namafia.api.entities.Game
import com.roragok.namafia.api.entities.GameDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NamafiaService {
    /**
     * Attempts to fetch all available games
     *
     * @since 1.0.0
     */
    @GET("getGames")
    fun fetchAllGames(): Single<List<Game>>

    /**
     * Attempts to fetch a specific game's details
     *
     * @since 1.0.0
     */
    @GET("getDays/{gameId}")
    fun fetchGameDetails(@Path("gameId") gameId: Int): Single<List<GameDetail>>
}