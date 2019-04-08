/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.dds.roragok.namafia.events.navigation

import android.app.Activity
import com.dds.roragok.namafia.events.Event

data class BackNavigationEvent(private val transition: Transition) : Event {
    override fun resolve(activity: Activity, viewGroupId: Int?) {
        activity.finish()
        activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
    }
}