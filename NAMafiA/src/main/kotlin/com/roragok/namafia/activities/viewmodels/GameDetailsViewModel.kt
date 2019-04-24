/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.roragok.namafia.R
import com.roragok.namafia.activities.adapters.recyclerviews.models.GameDetailsModel
import com.roragok.namafia.api.retrofit.NamafiaService
import com.roragok.namafia.events.EventFactory
import com.roragok.namafia.events.messages.StringContent
import com.roragok.namafia.extensions.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class GameDetailsViewModel @Inject constructor(
    eventFactory: EventFactory,
    private val namafiaService: NamafiaService
) : AbstractViewModel(eventFactory) {

    private val gameDetails = MutableLiveData<List<GameDetailsModel>>()

    val gameDetailsObservable: LiveData<List<GameDetailsModel>> = gameDetails

    fun onViewCreated(gameId: Int) {
        namafiaService
            .fetchGameDetails(gameId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ gameDetails ->
                Timber.d("fetched game details")

                this.gameDetails.value = gameDetails.map { GameDetailsModel(it) }.sorted()
            }, {
                Timber.e(it, "error fetching game details")

                events.value = eventFactory.newSnackbarEvent(StringContent(R.string.error_fetching_games_list))
            })
            .addTo(disposables)
    }
}