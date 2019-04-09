/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roragok.namafia.R
import com.roragok.namafia.events.Event
import com.roragok.namafia.events.EventFactory
import com.roragok.namafia.events.messages.StringContent
import io.reactivex.disposables.CompositeDisposable
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Abstract view model to easily dipose of any disposables not yet canceled or completed
 *
 * @since 1.0.0
 */
abstract class AbstractViewModel constructor(protected val eventFactory: EventFactory) : ViewModel() {
    protected val events = MutableLiveData<Event>()
    protected val disposables = CompositeDisposable()

    /**
     * Gets the observable for navigation
     *
     * @since 1.0.0
     */
    val eventsObservable: LiveData<Event> = events

    protected fun handleException(throwable: Throwable) {
        handleException(throwable, unhandledCallback = null)
    }

    protected fun handleException(throwable: Throwable, unhandledCallback: ((Throwable) -> Unit)? = null) {
        val event = when (throwable) {
            is SocketTimeoutException -> eventFactory.newSnackbarEvent(StringContent(R.string.error_network_timed_out))
            is IOException            -> eventFactory.newSnackbarEvent(StringContent(R.string.error_network_no_connection))
            else                      -> null
        }

        if (event == null) {
            if (unhandledCallback != null) {
                unhandledCallback.invoke(throwable)

                return
            }

            events.value = eventFactory.newSnackbarEvent(StringContent(R.string.error_network_unknown_error))
        } else {
            events.value = event
        }
    }

    override fun onCleared() {
        disposables.clear()

        super.onCleared()
    }
}