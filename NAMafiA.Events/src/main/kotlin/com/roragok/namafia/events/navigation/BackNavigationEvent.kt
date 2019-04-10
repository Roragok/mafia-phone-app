/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events.navigation

import android.app.Activity
import com.roragok.namafia.events.Event
import com.roragok.namafia.events.Transition

data class BackNavigationEvent(private val transition: Transition) : Event {
    override fun resolve(activity: Activity, viewGroupId: Int?) {
        activity.finish()
        activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
    }
}