/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.dds.roragok.namafia.events.navigation

import android.app.Activity
import android.content.Intent
import com.dds.roragok.namafia.events.Event

data class BackWithResultNavigationEvent(
    private val resultCode: Int,
    private val outputData: OutputData?,
    private val transition: Transition
) : Event {

    override fun resolve(activity: Activity, viewGroupId: Int?) {
        if (outputData == null) {
            activity.setResult(resultCode)
        } else {
            val intent = Intent()
            intent.putExtra(IntentExtra.OUTPUT_DATA, outputData)

            activity.setResult(resultCode, intent)
        }

        activity.finish()
        activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
    }
}