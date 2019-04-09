/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events

import android.app.Activity
import timber.log.Timber

/**
 * An event that should only be processed one time
 *
 * @since 1.0.0
 */
abstract class AbstractOneTimeEvent : Event {
    private var hasBeenHandled = false

    protected abstract fun executeEvent(activity: Activity, viewGroupId: Int? = null)

    override fun resolve(activity: Activity, viewGroupId: Int?) {
        if (hasBeenHandled) {
            Timber.d("event has already been handled")

            return
        }

        hasBeenHandled = true

        executeEvent(activity, viewGroupId)
    }
}