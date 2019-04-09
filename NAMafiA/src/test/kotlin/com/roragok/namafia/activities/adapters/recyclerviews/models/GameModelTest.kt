/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.adapters.recyclerviews.models

import com.dds.roragok.namafia.api.entities.Game
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GameModelTest {
    private lateinit var model: GameModel

    @Test
    fun `test search - title is null`() {
        model = GameModel(Game(id = 404, title = null, signedPlayers = listOf("player 1, Player 2")))

        assertThat(model.matchesSearch("it", ignoreCase = true)).isFalse()
    }

    @Test
    fun `test search - matches title - ignore case`() {
        model = GameModel(Game(id = 404, title = "Title", signedPlayers = listOf("player 1, Player 2")))

        assertThat(model.matchesSearch("it", ignoreCase = true)).isTrue()
    }

    @Test
    fun `test search - matches title - not ignore case`() {
        model = GameModel(Game(id = 404, title = "Title", signedPlayers = listOf("player 1, Player 2")))

        assertThat(model.matchesSearch("title", ignoreCase = false)).isFalse()
        assertThat(model.matchesSearch("Title", ignoreCase = false)).isTrue()
    }

    @Test
    fun `test search - matches id`() {
        model = GameModel(Game(id = 404, title = "Title", signedPlayers = listOf("player 1, Player 2")))

        assertThat(model.matchesSearch("40", ignoreCase = true)).isTrue()
    }

    @Test
    fun `test search - matches players`() {
        model = GameModel(Game(id = 404, title = "Title", signedPlayers = listOf("player 1, Player 2")))

        assertThat(model.matchesSearch("player", ignoreCase = true)).isTrue()
        assertThat(model.matchesSearch("playerz", ignoreCase = false)).isFalse()
    }
}