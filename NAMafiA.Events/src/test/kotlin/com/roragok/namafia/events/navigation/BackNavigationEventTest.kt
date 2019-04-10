/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events.navigation

import android.app.Activity
import com.roragok.namafia.events.Transition
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class BackNavigationEventTest {
    private val activity = mockk<Activity>(relaxUnitFun = true)

    @Test
    fun `test resolve`() {
        val transition = Transition.SLIDE_UP

        val event = BackNavigationEvent(transition)
        event.resolve(activity)

        verify {
            activity.finish()
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
        }
    }
}