/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.roragok.namafia.R
import com.roragok.namafia.activities.adapters.recyclerviews.models.GameModel
import com.roragok.namafia.api.retrofit.NamafiaService
import com.roragok.namafia.events.EventFactory
import com.roragok.namafia.events.messages.StringContent
import com.roragok.namafia.extensions.addTo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class GamesViewModel @Inject constructor(
    eventFactory: EventFactory,
    private val namfiaService: NamafiaService
) : AbstractViewModel(eventFactory) {

    private val allGames = mutableListOf<GameModel>()
    private val games = MutableLiveData<List<GameModel>>()

    val gamesObservable: LiveData<List<GameModel>> = games

    fun onViewCreated() {
        Timber.d("fetching games list")

        namfiaService
            .fetchAllGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ games ->
                Timber.d("fetched games: ${games.size}")

                allGames.clear()
                allGames += games.map { GameModel(it) }

                this.games.value = allGames
            }, {
                Timber.e(it, "error fetching games list")

                events.value = eventFactory.newSnackbarEvent(StringContent(R.string.error_fetching_games_list))
            })
            .addTo(disposables)
    }

    fun searchCriteriaChanged(searchText: String) {
        if (searchText.isBlank()) {
            Timber.d("search is blank; returning all games")

            games.value = allGames

            return
        }

        Flowable
            .fromIterable(allGames)
            .filter { it.matchesSearch(searchText, ignoreCase = true) }
            .subscribeOn(Schedulers.io())
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("search finished")

                games.value = it
            }, {
                Timber.e(it, "error searching games")
            })
            .addTo(disposables)
    }

    fun onGameClicked(game: GameModel) {
        Timber.d("game clicked: ${game.title} [${game.id}]")
    }
}