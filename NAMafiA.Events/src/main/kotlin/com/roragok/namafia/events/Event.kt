/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events

import android.app.Activity

/**
 * An event. In order to execute the event, call [Event.resolve]
 *
 * @since 1.0.0
 */
interface Event {
    /**
     * Resolves the event
     *
     * @param activity the current activity in which the event is to be resolved
     * @param viewGroupId the ID of the viewgroup to display the event. Only applicable to snackbar events.
     * @since 1.0.0
     */
    fun resolve(activity: Activity, viewGroupId: Int? = null)
}