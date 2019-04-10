/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events

import android.app.Activity
import android.content.Intent
import kotlin.reflect.KClass

data class NavigationEvent<T : KClass<out Activity>>(
    private val targetActivity: T,
    private val transition: Transition,
    private val finishCurrentActivity: Boolean,
    private val intentFlags: Int?,
    private val requestCode: Int?,
    private val inputData: InputData?
) : AbstractOneTimeEvent() {

    override fun executeEvent(activity: Activity, viewGroupId: Int?) {
        val intent = Intent(activity, targetActivity.java)

        intentFlags?.let {
            intent.addFlags(intentFlags)
        }

        inputData?.let {
            intent.putExtra(IntentExtra.INPUT_DATA, inputData)
        }

        if (requestCode == null) {
            activity.startActivity(intent)
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)

            if (finishCurrentActivity) {
                activity.finish()
            }

            return
        }

        activity.startActivityForResult(intent, requestCode)
        activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
    }
}